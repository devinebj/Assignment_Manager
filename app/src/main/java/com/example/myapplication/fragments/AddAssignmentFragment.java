package com.example.myapplication.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAssignmentFragment extends Fragment {
    private String originalAssignmentName = null;
    private View rootView;
    private Spinner courseSpinner;
    private EditText assignmentNameEditText, pointsPossibleEditText, gradeWeightEditText, dueDateEditText, dueTimeEditText;
    private MaterialButton addAssignmentButton, deleteAssignmentButton;
    private ArrayAdapter<String> spinnerAdapter;
    private TextView assignmentNameLabel, dueDateLabel, pointsPossibleLabel, dueTimeLabel;
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
        setupDueTimePicker();

        Bundle args = getArguments();
        if(args != null && "edit".equals(args.getString("mode"))) {
            populateFieldsForEdit(args);
        }
        setupAddAssignmentButton();

        return rootView;
    }

    private void initializeViews() {
        assignmentNameEditText = rootView.findViewById(R.id.assignment_name_et);
        pointsPossibleEditText = rootView.findViewById(R.id.points_possible_et);
        gradeWeightEditText = rootView.findViewById(R.id.grade_weight_et);
        courseSpinner = rootView.findViewById(R.id.courses_spinner);
        dueDateEditText = rootView.findViewById(R.id.due_date_et);
        dueTimeEditText = rootView.findViewById(R.id.due_time_et);
        assignmentNameLabel = rootView.findViewById(R.id.assignment_name_tv);
        pointsPossibleLabel = rootView.findViewById(R.id.points_possible_tv);
        dueDateLabel = rootView.findViewById(R.id.due_date_tv);
        dueTimeLabel = rootView.findViewById(R.id.due_time_tv);
        addAssignmentButton = rootView.findViewById(R.id.add_assignment_button);
        deleteAssignmentButton = rootView.findViewById(R.id.delete_assignment_button);
    }

    private void setupSpinner() {
        spinnerAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(spinnerAdapter);
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

    private void setupDueTimePicker() {
        dueTimeEditText.setOnClickListener(v -> {
           Calendar calendar = Calendar.getInstance();
           int hour = calendar.get(Calendar.HOUR_OF_DAY);
           int minute = calendar.get(Calendar.MINUTE);
           TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                   (view, selectedHour, selectedMinute) -> {
                        String selectedTime = String.format(Locale.US, "%02d:%02d", selectedHour, selectedMinute);
                        dueTimeEditText.setText(selectedTime);
                   }, hour, minute, true);
           timePickerDialog.show();
        });
    }

    private void populateFieldsForEdit(Bundle args){
        originalAssignmentName = args.getString("assignmentName");
        assignmentNameEditText.setText(args.getString("assignmentName"));
        pointsPossibleEditText.setText(String.valueOf(args.getInt("pointsPossible")));
        gradeWeightEditText.setText(String.valueOf(args.getInt("gradeWeight")));
        dueDateEditText.setText(args.getString("dueDate"));
        if (args.getString("dueTime") != null) {
            dueTimeEditText.setText(args.getString("dueTime"));
        }


        String courseName = args.getString("courseName");
        int spinnerPosition = spinnerAdapter.getPosition(courseName);
        courseSpinner.setSelection(spinnerPosition);

        TextView header = rootView.findViewById(R.id.assignment_header_tv);
        if(header != null){
            header.setText("Edit Assignment");
        }

        // Use the save button for edit
        addAssignmentButton.setText("Save Changes");
        deleteAssignmentButton.setVisibility(View.VISIBLE);
    }

    private void setupAddAssignmentButton() {
        addAssignmentButton.setOnClickListener(v -> {
            Bundle args = getArguments();
            boolean isEditMode = args != null && "edit".equals(args.getString("mode"));

            if (!validateInput()) {
                return;
            } else {
                resetRequired(assignmentNameLabel, "Assignment Name *");
                resetRequired(pointsPossibleLabel, "Points Possible *");
                resetRequired(dueDateLabel, "Due Date *");
                resetRequired(dueTimeLabel, "Due Time *");
            }

            String course = courseSpinner.getSelectedItem().toString();
            String assignmentName = assignmentNameEditText.getText().toString();
            int pointsPossible = Integer.parseInt(pointsPossibleEditText.getText().toString());
            int gradeWeight = gradeWeightEditText.getText().toString().isEmpty() ? 0
                    : Integer.parseInt(gradeWeightEditText.getText().toString());
            String dueDateString = dueDateEditText.getText().toString();
            String dueTimeString = dueTimeEditText.getText().toString();

            Date dueDate = parseDateString(dueDateString);
            if (dueDate == null) {
                showToast("Invalid date format: Please use MM/dd/yyyy.");
                return;
            }

            Date dueTime = parseTimeString(dueTimeString);
            if (dueTime == null){
                showToast("Invalid time format: Please use HH:mm.");
                return;
            }

            Assignment assignment = new Assignment(course, assignmentName, pointsPossible, gradeWeight, dueDate, dueTime);
            if(isEditMode){
                assignmentManager.updateAssignment(originalAssignmentName, assignment);
                showToast("Assignment Updated!");
            } else {
                assignmentManager.addAssignment(assignment);
                showToast("Assignment successfully added!");
            }

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

        if(courseSpinner.getSelectedItem() == null) {
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

    private Date parseTimeString(String timeString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        try {
            return simpleDateFormat.parse(timeString);
        } catch (Exception e) {
            return null;
        }
    }

    private void clearInputs() {
        assignmentNameEditText.setText("");
        gradeWeightEditText.setText("");
        pointsPossibleEditText.setText("");
        dueDateEditText.setText("");
        dueTimeEditText.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void markRequired(TextView label, String baseText){
        SpannableString spannable = new SpannableString(baseText);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, baseText.length(),
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        label.setText(spannable);
    }

    private void resetRequired(TextView label, String baseText) {
        SpannableString spannable = new SpannableString(baseText);
        int starIndex = baseText.length() - 1;
        spannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, starIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), starIndex, baseText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        label.setText(spannable);
    }
}
