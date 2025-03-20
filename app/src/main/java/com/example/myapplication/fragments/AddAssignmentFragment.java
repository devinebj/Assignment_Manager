package com.example.myapplication.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.managers.AssignmentManager;
import com.example.myapplication.managers.CourseManager;
import com.example.myapplication.models.Assignment;
import com.example.myapplication.models.Course;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddAssignmentFragment extends Fragment {

    private View rootView;
    private Spinner courseSpinner;
    private EditText assignmentNameEditText, pointsPossibleEditText, gradeWeightEditText, dueDateEditText;
    private Button addAssignmentButton;
    private ArrayAdapter<String> spinnerAdapter;
    private TextView assignmentNameLabel, dueDateLabel, pointsPossibleLabel;
    private AssignmentManager assignmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add, container, false);

        assignmentManager = AssignmentManager.getInstance(requireContext());

        initializeViews();
        setupSpinner();
        updateSpinner(CourseManager.getInstance(requireContext()).getCourses());
        setupDueDatePicker();
        setupAddAssignmentButton();

        return rootView;
    }

    private void initializeViews() {
        addAssignmentButton = rootView.findViewById(R.id.add_assignment_button);
        assignmentNameEditText = rootView.findViewById(R.id.assignment_name_et);
        pointsPossibleEditText = rootView.findViewById(R.id.points_possible_et);
        gradeWeightEditText = rootView.findViewById(R.id.grade_weight_et);
        courseSpinner = rootView.findViewById(R.id.courses_spinner);
        dueDateEditText = rootView.findViewById(R.id.due_date_et);
        assignmentNameLabel = rootView.findViewById(R.id.assignment_name_tv);
        pointsPossibleLabel = rootView.findViewById(R.id.points_possible_tv);
        dueDateLabel = rootView.findViewById(R.id.due_date_tv);
    }

    private void setupDueDatePicker() {
        dueDateEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Note: selectedMonth is zero-indexed.
                        String selectedDate = String.format(Locale.US, "%02d/%02d/%d",
                                selectedMonth + 1, selectedDay, selectedYear);
                        dueDateEditText.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void setupSpinner() {
        spinnerAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(spinnerAdapter);
    }

    private void setupAddAssignmentButton() {
        addAssignmentButton.setOnClickListener(v -> {
            if (!validateInput()) {
                return;
            } else {
                resetRequired(assignmentNameLabel, "Assignment Name *");
                resetRequired(pointsPossibleLabel, "Points Possible *");
                resetRequired(dueDateLabel, "Due Date *");
            }

            String course = courseSpinner.getSelectedItem().toString();
            String assignmentName = assignmentNameEditText.getText().toString();
            int pointsPossible = Integer.parseInt(pointsPossibleEditText.getText().toString());
            int gradeWeight = gradeWeightEditText.getText().toString().isEmpty() ? 0
                    : Integer.parseInt(gradeWeightEditText.getText().toString());
            String dueDateString = dueDateEditText.getText().toString();

            Date dueDate = parseDateString(dueDateString);
            if (dueDate == null) {
                showToast("Invalid date format: Please use MM/dd/yyyy.");
                return;
            }

            Assignment assignment = new Assignment(
                    course,
                    assignmentName,
                    pointsPossible,
                    gradeWeight,
                    dueDate
            );

            assignmentManager.addAssignment(assignment);
            showToast("Assignment successfully added!");
            clearInputs();
        });
    }

    private boolean validateInput() {
        boolean isValid = courseSpinner.getSelectedItem() != null;

        if (assignmentNameEditText.getText().toString().trim().isEmpty()) {
            markRequired(assignmentNameLabel, "Assignment Name *");
            isValid = false;
        }

        if (pointsPossibleEditText.getText().toString().trim().isEmpty()){
            markRequired(pointsPossibleLabel, "Points Possible *");
            isValid = false;
        }

        if (dueDateEditText.getText().toString().trim().isEmpty()) {
            markRequired(dueDateLabel, "Due Date *");
            isValid = false;
        }

        return isValid;
    }

    private Date parseDateString(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    private void clearInputs() {
        assignmentNameEditText.setText("");
        gradeWeightEditText.setText("");
        pointsPossibleEditText.setText("");
        dueDateEditText.setText("");
    }

    private void updateSpinner(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            showToast("No courses available. Please add a course first.");
            spinnerAdapter.clear();
            spinnerAdapter.notifyDataSetChanged();
            return;
        }

        List<String> courseNames = new ArrayList<>();
        for (Course course : courses) {
            courseNames.add(course.getName());
        }
        spinnerAdapter.clear();
        spinnerAdapter.addAll(courseNames);
        spinnerAdapter.notifyDataSetChanged();
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void markRequired(TextView label, String baseText){
        SpannableString spannable = new SpannableString(baseText);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, baseText.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        label.setText(spannable);
    }

    private void resetRequired(TextView label, String baseText) {
        SpannableString spannable = new SpannableString(baseText);
        spannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, baseText.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), baseText.length() - 1, baseText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        label.setText(spannable);
    }
}
