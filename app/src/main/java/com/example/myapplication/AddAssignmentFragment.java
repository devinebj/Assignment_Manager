package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddAssignmentFragment extends Fragment  implements Data.IObserver {
    private Spinner courseSpinner;
    private EditText dueDateEditText;
    private ArrayAdapter<String> spinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_fragment, container, false);

        courseSpinner = rootView.findViewById(R.id.courses_spinner);
        setupSpinner();

        Data.getInstance().registerObserver(this);
        updateSpinner(Data.getInstance().getCourses());

        dueDateEditText = rootView.findViewById(R.id.due_date_et);
        setupDueDatePicker();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Data.getInstance().unregisterObserver(this);
    }

    private void setupSpinner() {
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(spinnerAdapter);
    }

    private void updateSpinner(List<Course> courses){
        List<String> courseNames = new ArrayList<>();
        for(Course course : courses){
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

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedMonth + "/" + selectedDay + "/" + selectedYear;
                    dueDateEditText.setText(selectedDate);
                }, year, month, day);
            datePickerDialog.show();
        });
    }

    @Override
    public void onRecyclerDataChanged(List<Course> courses){
        updateSpinner(courses);
    }
}
