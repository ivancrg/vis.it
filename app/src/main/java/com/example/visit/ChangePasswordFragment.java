package com.example.visit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;


public class ChangePasswordFragment extends Fragment {

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        TextView changePasswordText = (TextView) view.findViewById(R.id.changePasswordTextView);
        TextInputEditText newPassword = (TextInputEditText) view.findViewById(R.id.newPasswordTextInputEditText);
        TextInputEditText confirmNewPassword = (TextInputEditText) view.findViewById(R.id.confirmNewPasswordTextInputEditText);
        Button confirm = (Button) view.findViewById(R.id.confirmButton);
        Button cancel = (Button) view.findViewById(R.id.cancelButton);


        confirm.setEnabled(false);

        TextWatcher confirmEnabledWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                confirm.setEnabled(newPassword.getText().length() > 0 && confirmNewPassword.getText().length() > 0);
            }
        };

        newPassword.addTextChangedListener(confirmEnabledWatcher);
        confirmNewPassword.addTextChangedListener(confirmEnabledWatcher);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change password method
                 FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentUserInterface, new UserInterfaceFragment());
                fragmentTransaction.commit();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to user interface fragment
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentUserInterface, new UserInterfaceFragment());
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}