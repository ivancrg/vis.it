package com.example.visit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;
import com.example.visit.database.UpdatePasswordPatch;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ChangePasswordFragment extends Fragment {
    // GifImageView for GIF that shows up while waiting for API to respond
    private pl.droidsonroids.gif.GifImageView loadingImageView;

    // Our custom password regex pattern used for validation
    // No whitespaces, minimum eight characters, at least one uppercase letter, one lowercase letter and one number
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        TextInputEditText newPassword = (TextInputEditText) view.findViewById(R.id.newPasswordTextInputEditText);
        TextInputEditText confirmNewPassword = (TextInputEditText) view.findViewById(R.id.confirmNewPasswordTextInputEditText);
        Button confirm = (Button) view.findViewById(R.id.confirmButton);
        Button cancel = (Button) view.findViewById(R.id.cancelButton);
        loadingImageView = (pl.droidsonroids.gif.GifImageView) view.findViewById(R.id.changePasswordFragmentLoading);

        confirm.setEnabled(false);

        TextWatcher confirmEnabledWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // we don't need this event
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // we don't need this event
            }

            @Override
            public void afterTextChanged(Editable editable) {
                confirm.setEnabled(Objects.requireNonNull(newPassword.getText()).length() > 0 && Objects.requireNonNull(confirmNewPassword.getText()).length() > 0 &&
                        newPassword.getText().toString().equals(confirmNewPassword.getText().toString()));
            }
        };

        newPassword.addTextChangedListener(confirmEnabledWatcher);
        confirmNewPassword.addTextChangedListener(confirmEnabledWatcher);

        confirm.setOnClickListener(view1 -> {
            if (informationValid(newPassword, confirmNewPassword)) {
                // Showing the waiting GIF
                loadingImageView.setVisibility(View.VISIBLE);

                updatePassword(view1, LoggedUser.getUsername(), Objects.requireNonNull(newPassword.getText()).toString());
            }
        });

        cancel.setOnClickListener(v -> {
            //back to user interface fragment
            MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), new UserInterfaceFragment(), true);
        });
        return view;
    }

    private boolean informationValid(EditText newPassword, EditText confirmNewPassword) {
        boolean valid = true;

        if (newPassword.getText().toString().isEmpty() || !PASSWORD_PATTERN.matcher(newPassword.getText().toString()).matches()) {
            valid = false;
            newPassword.setError("Please enter a valid password. It should contain uppercase letter, lowercase letter, one number, minimum 8 characters without whitespaces.");
        }

        if (confirmNewPassword.getText().toString().isEmpty() || !PASSWORD_PATTERN.matcher(confirmNewPassword.getText().toString()).matches()) {
            valid = false;
            confirmNewPassword.setError("Please enter a valid password. It should contain uppercase letter, lowercase letter, one number, minimum 8 characters without whitespaces.");
        }

        return valid;
    }

    private void updatePassword(View view, String username, String newPassword) {
        // Hashing variable newPassword and storing it to newPasswordHashed
        String newPasswordHashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        //change password method
        UpdatePasswordPatch updatePasswordPatch = new UpdatePasswordPatch(username, newPasswordHashed);

        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);

        Call<UpdatePasswordPatch> call = herokuAPI.updatePassword(username, updatePasswordPatch);

        call.enqueue(new Callback<UpdatePasswordPatch>() {
            @Override
            public void onResponse(@NotNull Call<UpdatePasswordPatch> call, @NotNull Response<UpdatePasswordPatch> response) {
                // Hiding the waiting GIF
                loadingImageView.setVisibility(View.GONE);

                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/update", "notSuccessful: Something went wrong. " + response.code());
                    return;
                }

                UpdatePasswordPatch postResponse = response.body();

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

        MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), new UserInterfaceFragment(), false);
    }
}