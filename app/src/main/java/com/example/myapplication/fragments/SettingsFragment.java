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

import com.example.myapplication.fragments.ViewModels.Course;
import com.example.myapplication.R;
import com.example.myapplication.fragments.ViewModels.SettingsData;

import java.util.List;

public class SettingsFragment extends Fragment implements SettingsData.IObserver {
    Button addClassButton;
    EditText daysToNotifyET;
    CourseAdapter courseAdapter;
    RecyclerView coursesRecyclerView;
    View rootView;
    SettingsData settingsData;
    List<Course> courses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        settingsData = SettingsData.getInstance();
        settingsData.registerObserver(this);
        courses = settingsData.getCourses();

        initializeViews();
        setupAddClassButton(inflater);
        setupDaysToNotifyEditText();
        setupRecyclerView();

        return rootView;
    }

    private void initializeViews(){
        addClassButton = rootView.findViewById(R.id.addCourseButton);
        daysToNotifyET = rootView.findViewById(R.id.daysToNotifyET);
        coursesRecyclerView = rootView.findViewById(R.id.courses_rv);
    }

    private void setupRecyclerView() {

        courseAdapter = new CourseAdapter(requireContext(), courses, this::onCourseSelected);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        coursesRecyclerView.setAdapter(courseAdapter);
    }

    private void onCourseSelected(Course course){}

    private void setupAddClassButton(LayoutInflater inflater){
        addClassButton.setOnClickListener(v -> {
            View popupView = createPopUpView(inflater);
            PopupWindow popupWindow = createPopupWindow(popupView);

            setupPopupDismissListener(popupWindow);
            setupPopupButton(popupView, popupWindow);
        });
    }

    private View createPopUpView(LayoutInflater inflater){
        return inflater.inflate(R.layout.add_course_popout, null);
    }

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

    private void setupPopupDismissListener(PopupWindow popupWindow) {
        popupWindow.setOnDismissListener(this::restoreBackground);
    }

    private void setupPopupButton(View popupView, PopupWindow popupWindow){
        Button popupButton = popupView.findViewById(R.id.popup_Button);
        EditText popupEditText = popupView.findViewById(R.id.popup_EditText);

        popupButton.setOnClickListener(v -> handlePopUpButtonClick(popupEditText, popupWindow));
    }

    private void handlePopUpButtonClick(EditText popupEditText, PopupWindow popupWindow){
        String textInput = popupEditText.getText().toString().trim();

        if(textInput.isEmpty()){
            showToast("Class name cannot be empty!");
        } else {
            settingsData.getCourses().add(new Course(textInput));
            settingsData.notifyRecyclerDataChanged();
            popupWindow.dismiss();
        }
    }

    private void setupDaysToNotifyEditText() {
        daysToNotifyET.setOnClickListener(v -> validateDaysToNotify());
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

    @Override
    public void onRecyclerDataChanged(List<Course> courses) {
    }
}