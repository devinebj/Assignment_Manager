package com.example.myapplication;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
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

public class SettingsFragment extends Fragment {
    Button addClassButton;
    EditText daysToNotifyET;
    View rootView;
    View popUpView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        addClassButton = rootView.findViewById(R.id.addClassButton);
        daysToNotifyET = rootView.findViewById((R.id.daysToNotifyET));

        //Add class button
        addClassButton.setOnClickListener(v -> {
            popUpView = inflater.inflate(R.layout.add_class_popout, null);

            PopupWindow popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setAnimationStyle(androidx.appcompat.R.style.Base_Animation_AppCompat_Tooltip);
            popupWindow.setOutsideTouchable(true);
            popupWindow.showAtLocation(rootView, Gravity.CENTER, 0 , 0);
            dimBackground();

            Button popUpButton = popUpView.findViewById(R.id.popup_Button);
            EditText popupEditText = popUpView.findViewById(R.id.popup_EditText);

            popupWindow.setOnDismissListener(() -> {
                restoreBackground();
            });

            popUpButton.setOnClickListener(v1 -> {
                String textInput = popupEditText.getText().toString();

                if(textInput.isBlank() || textInput.isEmpty()){
                    Toast.makeText(requireContext(), "Class name cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), textInput, Toast.LENGTH_SHORT).show();
                    popupWindow.setAnimationStyle(androidx.appcompat.R.style.Base_Animation_AppCompat_Tooltip);
                    popupWindow.dismiss();
                }
            });
        });

        //Days to notify EditText
        daysToNotifyET.setOnClickListener(v -> {
            final int MAX_NUMBER = 7;
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

        return rootView;
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
}
