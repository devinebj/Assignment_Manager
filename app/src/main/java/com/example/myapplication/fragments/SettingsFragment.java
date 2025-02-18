package com.example.myapplication.fragments;

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

public class SettingsFragment extends Fragment {

    // UI Elements
    Button addClassButton;
    EditText daysToNotifyET;
    RecyclerView coursesRecyclerView;
    View rootView;

    // Managers and data
    CourseAdapter courseAdapter;
    CourseManager courseManager;
    SettingsManager settingsManager;
    ArrayList<Course> courses;

    // Constants
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
        setupRecyclerView();
        setupAddClassButton(inflater);
        setupDaysToNotifyEditText();

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        // Reload courses from the settings on resume
        courses = SettingsManager.getInstance(requireContext()).loadCourses();
        courseAdapter.updateData(courses);
    }

    // Find and initialize all the view components
    private void initializeViews(){
        addClassButton = rootView.findViewById(R.id.add_course_button);
        daysToNotifyET = rootView.findViewById(R.id.days_to_notify_et);
        coursesRecyclerView = rootView.findViewById(R.id.courses_rv);

        // Initialize the days-to-notify field with the saved value
        daysToNotifyET.setText(String.valueOf(SettingsManager.getInstance(requireContext()).loadDaysToNotify()));
    }

    // Setup the RecyclerView with its adapter and layout manager.
    private void setupRecyclerView() {
        courseAdapter = new CourseAdapter(requireContext(), courses);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        coursesRecyclerView.setAdapter(courseAdapter);
    }

    // Attach a click listener to the "Add Class" button to show the popup.
    private void setupAddClassButton(LayoutInflater inflater){
        addClassButton.setOnClickListener(v -> showAddCoursePopup(inflater));
    }

    // Inflate and display the popup window for adding a new course
    private void showAddCoursePopup(final LayoutInflater inflater){
        View popupView = inflater.inflate(R.layout.popout_add_course, (ViewGroup) rootView, false);
        PopupWindow popupWindow = createPopupWindow(popupView);
        setupPopupDismissListener(popupWindow);
        setupPopupButton(popupView, popupWindow);
    }

    // Create and show a PopupWindow with the specified view
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

    // Restore the background brightness when the popup is dismissed
    private void setupPopupDismissListener(@NonNull PopupWindow popupWindow) {
        popupWindow.setOnDismissListener(this::restoreBackground);
    }

    // Set up the button inside the popup window to handle adding a new course
    private void setupPopupButton(@NonNull View popupView, PopupWindow popupWindow){
        Button popupButton = popupView.findViewById(R.id.popup_Button);
        EditText popupEditText = popupView.findViewById(R.id.popup_EditText);

        popupButton.setOnClickListener(v -> handleAddCourse(popupEditText, popupWindow));
    }

    // Handle the logic when adding a new course from the popup
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

    // Setup a focus change listener for the daysToNotify EditText to validate input
    private void setupDaysToNotifyEditText() {
        daysToNotifyET.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                validateDaysToNotify();
            }
        });
    }

    // Validate the user input for the days-to-notify field
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

    // Dim the background when the popup is shown
    private void dimBackground(){
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        getActivity().getWindow().setAttributes(params);
    }

    // Restore the background brightness when the popup is dismissed
    private void restoreBackground(){
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 1f;
        getActivity().getWindow().setAttributes(params);
    }

    // Utility method to show a short Toast message
    private void showToast(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
