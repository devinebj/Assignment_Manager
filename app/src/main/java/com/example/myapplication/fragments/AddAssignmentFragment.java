package com.example.myapplication.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.fragments.ViewModels.Course;
import com.example.myapplication.fragments.ViewModels.SettingsData;

import java.util.ArrayList;
import java.util.List;

public class AddAssignmentFragment extends Fragment  implements SettingsData.IObserver {
    private Spinner courseSpinner;
    private ArrayAdapter<String> spinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_fragment, container, false);

        courseSpinner = rootView.findViewById(R.id.courses_spinner);
        setupSpinner();

        SettingsData.getInstance().registerObserver(this);
        updateSpinner(SettingsData.getInstance().getCourses());

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SettingsData.getInstance().unregisterObserver(this);
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

    @Override
    public void onRecyclerDataChanged(List<Course> courses){
        updateSpinner(courses);
    }
}
