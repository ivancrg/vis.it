package com.example.visit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;
import com.example.visit.database.UpdatePasswordPatch;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


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
                confirm.setEnabled(newPassword.getText().length() > 0 && confirmNewPassword.getText().length() > 0 &&
                        newPassword.getText().toString().equals(confirmNewPassword.getText().toString()));
            }
        };

        newPassword.addTextChangedListener(confirmEnabledWatcher);
        confirmNewPassword.addTextChangedListener(confirmEnabledWatcher);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(informationValid(newPassword.getText().toString(), confirmNewPassword.getText().toString())){
                    updatePassword(view, LoggedUser.getUsername(), newPassword.getText().toString());
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to user interface fragment
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLogin, new UserInterfaceFragment());
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private boolean informationValid(String oldPassword, String newPassword) {
        // TODO CHECK INFO

        return true;
    }

    private void updatePassword(View view, String username, String newPassword) {
        //change password method
        UpdatePasswordPatch updatePasswordPatch = new UpdatePasswordPatch(username, newPassword);

        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);

        Call<UpdatePasswordPatch> call = herokuAPI.updatePassword(username, updatePasswordPatch);

        call.enqueue(new Callback<UpdatePasswordPatch>() {
            @Override
            public void onResponse(@NotNull Call<UpdatePasswordPatch> call, @NotNull Response<UpdatePasswordPatch> response) {
                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/update", "notSuccessful: Something went wrong. " + response.code());
                    return;
                }

                UpdatePasswordPatch postResponse = response.body();
                //Toast.makeText(view.getContext(), response.body().toString(), Toast.LENGTH_LONG).show();

                assert postResponse != null;

                if ("password_updated".equals(postResponse.getFeedback())) {// User found
                    Toast.makeText(view.getContext(), "Password successfully updated.", Toast.LENGTH_LONG).show();
                } else {// Possible database error server-side, user not found...
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdatePasswordPatch> call, @NotNull Throwable t) {
                Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                Log.e("/updatePassword", "onFailure: Something went wrong. " + t.getMessage());
            }
        });

        FragmentTransaction fragmentTransaction = getActivity()
                .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLogin, new UserInterfaceFragment());
        fragmentTransaction.commit();
    }
}