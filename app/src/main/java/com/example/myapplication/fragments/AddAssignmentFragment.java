package com.example.myapplication.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.managers.AssignmentManager;
import com.example.myapplication.models.Assignment;
import com.example.myapplication.models.Course;
import com.example.myapplication.managers.CourseManager;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddAssignmentFragment extends Fragment {
    private View rootView;
    private Spinner courseSpinner;
    private EditText assignmentNameEditText;
    private EditText gradeWeightEditText;
    private EditText dueDateEditText;
    private Button addAssignmentButton;
    private ArrayAdapter<String> spinnerAdapter;
    private AssignmentManager assignmentManagar;
    private List<Assignment> assignments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add, container, false);

        assignmentManagar = AssignmentManager.getInstance(requireContext());
        assignments = assignmentManagar.getAssignments();

        initializeViews();
        setupAddAssignmentButton();
        setupDueDatePicker();
        setupSpinner();
        updateSpinner(CourseManager.getInstance(requireContext()).getCourses());

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initializeViews(){
        addAssignmentButton = rootView.findViewById(R.id.add_assignment_button);
        assignmentNameEditText = rootView.findViewById(R.id.assignment_name_et);
        gradeWeightEditText = rootView.findViewById(R.id.grade_weight_et);
        courseSpinner = rootView.findViewById(R.id.courses_spinner);
        dueDateEditText = rootView.findViewById(R.id.due_date_et);
    }

    private void setupDueDatePicker() {
        dueDateEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = selectedMonth + "/" + selectedDay + "/" + selectedYear;
                        dueDateEditText.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void setupSpinner() {
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(spinnerAdapter);
    }

    private void setupAddAssignmentButton() {
        addAssignmentButton.setOnClickListener(v -> {
            Object selectedItem = courseSpinner.getSelectedItem();
            if(selectedItem == null){
                showToast("Please select a course!");
                return;
            }

            String course = courseSpinner.getSelectedItem().toString();
            String assignmentName = assignmentNameEditText.getText().toString();
            int gradeWeight = gradeWeightEditText.getText().toString().isEmpty() ? 0 : Integer.parseInt(gradeWeightEditText.getText().toString());
            String dueDate = dueDateEditText.getText().toString();

            if(course.isEmpty()) {
                showToast("Course cannot be empty!");
                return;
            }

            if(assignmentName.isEmpty()) {
                showToast("Assignment Name cannot be empty!");
                return;
            }

            if(dueDate.isEmpty()) {
                showToast("Due Date cannot be empty!");
                return;
            }

            Assignment assignment = new Assignment(
                course,
                assignmentName,
                gradeWeight,
                dueDate
            );

            AssignmentManager.getInstance(requireContext()).addAssignment(assignment);
        });
    }

    private void updateSpinner(List<Course> courses){
        if(courses == null || courses.isEmpty()) {
            showToast("No courses available. Please add a course first.");
            spinnerAdapter.clear();
            spinnerAdapter.notifyDataSetChanged();
            return;
        }

        List<String> courseNames = new ArrayList<>();
        for(Course course : courses){
            courseNames.add(course.getName());
        }
        spinnerAdapter.clear();
        spinnerAdapter.addAll(courseNames);
        spinnerAdapter.notifyDataSetChanged();
    }

    private void showToast(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
