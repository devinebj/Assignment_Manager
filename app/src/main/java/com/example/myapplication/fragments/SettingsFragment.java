package com.example.myapplication.fragments;

import android.os.Bundle;
import android.provider.Settings;
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
import com.example.myapplication.models.AppSettings;
import com.example.myapplication.models.Course;
import com.example.myapplication.adapters.CourseAdapter;
import com.example.myapplication.managers.CourseManager;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    Button addClassButton;
    EditText daysToNotifyET;
    CourseAdapter courseAdapter;
    RecyclerView coursesRecyclerView;
    View rootView;
    CourseManager courseManager;
    List<Course> courses;
    SettingsManager settingsManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        courseManager = CourseManager.getInstance(requireContext());
        courses = courseManager.getCourses();

        initializeViews();
        setupAddClassButton(inflater);
        setupDaysToNotifyEditText();
        setupRecyclerView();

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        courses = settingsManager.loadCourses();
        courseAdapter.updateData(courses);
    }

    private void initializeViews(){
        addClassButton = rootView.findViewById(R.id.add_course_button);
        daysToNotifyET = rootView.findViewById(R.id.days_to_notify_et);
        coursesRecyclerView = rootView.findViewById(R.id.courses_rv);
        daysToNotifyET.setText(String.valueOf(settingsManager.loadDaysToNotify()));
    }

    private void setupRecyclerView() {
        courseAdapter = new CourseAdapter(requireContext(), courses);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        coursesRecyclerView.setAdapter(courseAdapter);
    }

    private void setupAddClassButton(LayoutInflater inflater){
        addClassButton.setOnClickListener(v -> {
            View popupView = inflater.inflate(R.layout.popout_add_course, (ViewGroup) rootView, false);
            PopupWindow popupWindow = createPopupWindow(popupView);

            setupPopupDismissListener(popupWindow);
            setupPopupButton(popupView, popupWindow);
        });
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

        popupButton.setOnClickListener(v -> {
            String textInput = popupEditText.getText().toString().trim();

            if (textInput.isEmpty()){
                showToast("Class name cannot be empty!");
            } else {
                Course newCourse = new Course(textInput);
                courses.add(newCourse);
                settingsManager.saveCourses(courses);
                courseAdapter.updateData(courses);
                popupWindow.dismiss();
            }
        });
    }

    private void setupDaysToNotifyEditText() {
        daysToNotifyET.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                validateDaysToNotify();
            }
        });
    }

    private void validateDaysToNotify(){
        final int MAX_NUMBER = 7;
        String input = daysToNotifyET.getText().toString();

        if(TextUtils.isEmpty(input)){
            showToast("Please enter a number.");
            return;
        }

        try {
            int number = Integer.parseInt(input);

            if(number > MAX_NUMBER){
                showToast("This number is too large! Max number is " + MAX_NUMBER);
                daysToNotifyET.setText(String.valueOf(MAX_NUMBER));
                daysToNotifyET.setSelection(String.valueOf(MAX_NUMBER).length());
            } else {
                daysToNotifyET.clearFocus();
                rootView.requestFocus();
            }

        } catch (NumberFormatException e){
            showToast("Invalid number!");
        }
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
