package com.example.myapplication.fragments;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
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

/**
 * Fragment for adding a new assignment.
 */
public class AddAssignmentFragment extends Fragment {

    private View rootView;
    private Spinner courseSpinner;
    private EditText assignmentNameEditText;
    private EditText pointsPossibleEditText;
    private EditText gradeWeightEditText;
    private EditText dueDateEditText;
    private Button addAssignmentButton;
    private ArrayAdapter<String> spinnerAdapter;

    private AssignmentManager assignmentManager;
    private List<Assignment> assignments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add, container, false);

        // Initialize the managers and assignment list.
        assignmentManager = AssignmentManager.getInstance(requireContext());
        assignments = assignmentManager.getAssignments();

        // Setup UI components.
        initializeViews();
        setupSpinner();
        updateSpinner(CourseManager.getInstance(requireContext()).getCourses());
        setupDueDatePicker();
        setupAddAssignmentButton();

        return rootView;
    }

    /**
     * Finds and assigns the UI components from the layout.
     */
    private void initializeViews() {
        addAssignmentButton = rootView.findViewById(R.id.add_assignment_button);
        assignmentNameEditText = rootView.findViewById(R.id.assignment_name_et);
        pointsPossibleEditText = rootView.findViewById(R.id.points_possible_et);
        gradeWeightEditText = rootView.findViewById(R.id.grade_weight_et);
        courseSpinner = rootView.findViewById(R.id.courses_spinner);
        dueDateEditText = rootView.findViewById(R.id.due_date_et);
    }

    /**
     * Configures the due date EditText to open a DatePickerDialog.
     */
    private void setupDueDatePicker() {
        dueDateEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Note: selectedMonth is zero-indexed.
                        String selectedDate = String.format(Locale.US, "%02d/%02d/%d",
                                selectedMonth + 1, selectedDay, selectedYear);
                        dueDateEditText.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    /**
     * Initializes the spinner adapter and assigns it to the course spinner.
     */
    private void setupSpinner() {
        spinnerAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(spinnerAdapter);
    }

    /**
     * Configures the "Add Assignment" button click listener to validate input,
     * create a new assignment, add it via the AssignmentManager, and clear inputs.
     */
    private void setupAddAssignmentButton() {
        addAssignmentButton.setOnClickListener(v -> {
            if (!validateInput()) {
                return;
            }

            String course = courseSpinner.getSelectedItem().toString();
            String assignmentName = assignmentNameEditText.getText().toString();
            int pointsPossible = pointsPossibleEditText.getText().toString().isEmpty() ? 0
                    : Integer.parseInt(pointsPossibleEditText.getText().toString());
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

    /**
     * Validates that required inputs are not empty.
     *
     * @return true if inputs are valid; false otherwise.
     */
    private boolean validateInput() {
        if (courseSpinner.getSelectedItem() == null) {
            showToast("Please select a course!");
            return false;
        }
        if (assignmentNameEditText.getText().toString().trim().isEmpty()) {
            showToast("Assignment Name cannot be empty!");
            return false;
        }
        if (dueDateEditText.getText().toString().trim().isEmpty()) {
            showToast("Due Date cannot be empty!");
            return false;
        }
        return true;
    }

    /**
     * Parses a date string in the format "MM/dd/yyyy".
     *
     * @param dateString the date string.
     * @return the parsed Date or null if parsing fails.
     */
    private Date parseDateString(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Clears all input fields.
     */
    private void clearInputs() {
        assignmentNameEditText.setText("");
        gradeWeightEditText.setText("");
        pointsPossibleEditText.setText("");
        dueDateEditText.setText("");
    }

    /**
     * Updates the spinner with the names of the available courses.
     *
     * @param courses the list of courses.
     */
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

    /**
     * Displays a short Toast message.
     *
     * @param message the message to display.
     */
    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
