package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    Button addClassButton;
    EditText daysToNotifyET;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        addClassButton = rootView.findViewById(R.id.addClassButton);
        daysToNotifyET = rootView.findViewById((R.id.daysToNotifyET));

        initiateListeners();

        return rootView;
    }

    private void initiateListeners(){
        addClassButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(),
                    "This is a test",
                    Toast.LENGTH_SHORT).show();
        });


        final int MAX_NUMBER = 14;
        daysToNotifyET.setOnClickListener(v -> {
            String input = daysToNotifyET.getText().toString();

            if(TextUtils.isEmpty(input)){
                Toast.makeText(requireContext(),
                        "Please enter a number",
                        Toast.LENGTH_SHORT).show();
            }

            try{
                int number = Integer.parseInt(input);

                if(number > MAX_NUMBER) {
                    Toast.makeText(requireContext(),
                            "This number is too large! Max is " + MAX_NUMBER,
                            Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e){
                Toast.makeText(requireContext(),
                        "Invalid number!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
