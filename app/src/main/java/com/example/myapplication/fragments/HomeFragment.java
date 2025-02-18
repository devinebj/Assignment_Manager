package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapplication.R;

public class HomeFragment extends Fragment {
    private Button monthViewBtn, weekViewBtn, dayViewBtn, newEventBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(rootView);
        setupListeners();
        return rootView;
    }

    private void initViews(View view) {
        monthViewBtn = view.findViewById(R.id.monthViewBtn);
        weekViewBtn = view.findViewById(R.id.weekViewBtn);
        dayViewBtn = view.findViewById(R.id.dayViewBtn);
        newEventBtn = view.findViewById(R.id.newEventBtn);
    }

    private void setupListeners() {
        monthViewBtn.setOnClickListener(v -> replaceFragment(new MonthViewFragment()));
        weekViewBtn.setOnClickListener(v -> replaceFragment(new WeekViewFragment()));
        dayViewBtn.setOnClickListener(v -> replaceFragment(new DailyCalendarFragment()));
        newEventBtn.setOnClickListener(v -> replaceFragment(new EventEditFragment()));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
