package com.example.myapplication.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.managers.SettingsManager;
import com.example.myapplication.models.Course;
import com.example.myapplication.adapters.CourseAdapter;
import com.example.myapplication.managers.CourseManager;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SettingsFragment extends Fragment {

    Button addCourseButton;
    EditText daysToNotifyET, semesterEndDateET;
    RecyclerView coursesRecyclerView;
    View rootView;

    CourseAdapter courseAdapter;
    CourseManager courseManager;
    SettingsManager settingsManager;
    List<Course> courses;

    private static final int MAX_DAYS_TO_NOTIFY = 7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize managers and data sources
        courseManager = CourseManager.getInstance(requireContext());
        settingsManager = SettingsManager.getInstance(requireContext());
        courses = courseManager.getCourses();

        initializeViews();
        semesterEndDateET.setOnClickListener(v -> showDatePicker());
        setupRecyclerView();
        setupAddCourseButton(inflater);
        setupDaysToNotifyEditText();

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateCourses();
    }

    private void initializeViews(){
        addCourseButton = rootView.findViewById(R.id.add_course_button);
        daysToNotifyET = rootView.findViewById(R.id.days_to_notify_et);
        coursesRecyclerView = rootView.findViewById(R.id.courses_rv);
        semesterEndDateET = rootView.findViewById(R.id.semester_end_date_et);

        // Initialize the days-to-notify field with the saved value
        daysToNotifyET.setText(String.valueOf(SettingsManager.getInstance(requireContext()).loadDaysToNotify()));

        String savedDate = settingsManager.loadSemesterEndDate();
        if (!TextUtils.isEmpty(savedDate)) {
            semesterEndDateET.setText(savedDate);
        }
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String dateString = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                    semesterEndDateET.setText(dateString);
                    settingsManager.saveSemesterEndDate(dateString);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void setupRecyclerView() {
        courseAdapter = new CourseAdapter(requireContext(), courses);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        coursesRecyclerView.setAdapter(courseAdapter);
    }

    private void setupAddCourseButton(LayoutInflater inflater){
        addCourseButton.setOnClickListener(v -> showAddCoursePopup(inflater));
    }

    private void showAddCoursePopup(final LayoutInflater inflater){
        View popupView = inflater.inflate(R.layout.popout_add_course, (ViewGroup) rootView, false);
        PopupWindow popupWindow = createPopupWindow(popupView);
        setupPopupDismissListener(popupWindow);
        setupPopupButton(popupView, popupWindow);
    }

    @NonNull
    private PopupWindow createPopupWindow(View popupView){
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.Animation);
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        dimBackground();
        return popupWindow;
    }

    private void setupPopupDismissListener(@NonNull PopupWindow popupWindow) {
        popupWindow.setOnDismissListener(this::restoreBackground);
    }

    private void setupPopupButton(@NonNull View popupView, PopupWindow popupWindow){
        Button popupButton = popupView.findViewById(R.id.popup_Button);
        EditText popupEditText = popupView.findViewById(R.id.popup_EditText);
        popupButton.setOnClickListener(v -> handleAddCourse(popupEditText, popupWindow));
    }

    private void handleAddCourse(EditText popupEditText, PopupWindow popupWindow){
        String courseName = popupEditText.getText().toString().trim();
        if (courseName.isEmpty()){
            showToast("Class name cannot be empty!");
        } else {
            Course newCourse = new Course(courseName);
            courses.add(newCourse);
            settingsManager.saveCourses(courses);
            courseAdapter.updateData(courses);
            popupWindow.dismiss();
        }
    }

    private void setupDaysToNotifyEditText() {
        daysToNotifyET.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                validateDaysToNotify();
            }
        });
    }

    private void validateDaysToNotify(){
        String input = daysToNotifyET.getText().toString();

        if(TextUtils.isEmpty(input)){
            showToast("Please enter a number.");
            return;
        }

        try {
            int number = Integer.parseInt(input);

            if(number > MAX_DAYS_TO_NOTIFY){
                showToast("This number is too large! Max number is " + MAX_DAYS_TO_NOTIFY);
                daysToNotifyET.setText(String.valueOf(MAX_DAYS_TO_NOTIFY));
                daysToNotifyET.setSelection(String.valueOf(MAX_DAYS_TO_NOTIFY).length());
            } else {
                daysToNotifyET.clearFocus();
                rootView.requestFocus();
            }

        } catch (NumberFormatException e){
            showToast("Invalid number!");
        }
    }

    private void updateCourses(){
        courses = settingsManager.loadCourses();
        courseAdapter.updateData(courses);
    }

    private void dimBackground(){
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        getActivity().getWindow().setAttributes(params);
    }

    private void restoreBackground(){
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 1f;
        getActivity().getWindow().setAttributes(params);
    }

    private void showToast(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
