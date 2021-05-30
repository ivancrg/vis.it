package com.example.visit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;
import com.example.visit.database.UpdatePatch;
import com.example.visit.database.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserInterfaceFragment extends Fragment {
    private pl.droidsonroids.gif.GifImageView loadingImageView;

    public UserInterfaceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_interface, container, false);

        TextView fullName = (TextView) view.findViewById(R.id.fullNameTextView);
        TextView labelUsername = (TextView) view.findViewById(R.id.usernameTextView);
        TextInputEditText firstName = (TextInputEditText) view.findViewById(R.id.firstNameTextInputEditText);
        TextInputEditText lastName = (TextInputEditText) view.findViewById(R.id.lastNameTextInputEditText);
        TextInputEditText email = (TextInputEditText) view.findViewById(R.id.emailTextInputEditText);
        Button update = (Button) view.findViewById(R.id.updateButton);
        Button changePassword = (Button) view.findViewById(R.id.changePasswordButton);
        Button myTrips = (Button) view.findViewById(R.id.myTripsButton);
        loadingImageView = (pl.droidsonroids.gif.GifImageView) view.findViewById(R.id.userInterfaceFragmentLoading);

        NavigationView navigationView = (NavigationView) Objects.requireNonNull(getActivity()).findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navigationLabelFullName = (TextView) headerView.findViewById(R.id.navigation_full_name);
        TextView navigationLabelUsername = (TextView) headerView.findViewById(R.id.navigation_username);

        navigationView.setCheckedItem(R.id.nav_account);

        if (LoggedUser.getIsLoggedIn()) {
            fullName.setText(String.format("%s %s", LoggedUser.getFirstName(), LoggedUser.getLastName()));
            navigationLabelFullName.setText(String.format("%s %s", LoggedUser.getFirstName(), LoggedUser.getLastName()));
            labelUsername.setText(LoggedUser.getUsername());
            navigationLabelUsername.setText(LoggedUser.getUsername());
            firstName.setText(LoggedUser.getFirstName());
            lastName.setText(LoggedUser.getLastName());
            email.setText(LoggedUser.getEmail());
        } else {
            fullName.setText("- -");
            labelUsername.setText("-");
            firstName.setText("-");
            lastName.setText("-");
            email.setText("-");
        }

        TextWatcher updateEnableWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                update.setEnabled(firstName.getText().length() > 0 &&
                        lastName.getText().length() > 0 &&
                        email.getText().length() > 0);
            }
        };

        firstName.addTextChangedListener(updateEnableWatcher);
        lastName.addTextChangedListener(updateEnableWatcher);
        email.addTextChangedListener(updateEnableWatcher);

        update.setOnClickListener(v -> {
            if (informationValid(firstName, lastName, email)) {
                // Showing the waiting GIF
                loadingImageView.setVisibility(View.VISIBLE);

                updateUser(view, firstName.getText().toString(), lastName.getText().toString(), email.getText().toString());
            }
        });

        changePassword.setOnClickListener(v -> {
            MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new ChangePasswordFragment(), true);
        });

        myTrips.setOnClickListener(v -> {
            MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new MyTripsFragment(), true);
        });

        return view;
    }

    private boolean informationValid(EditText firstName, EditText lastName, EditText email) {
        boolean valid = true;

        if (firstName.getText().toString().isEmpty()) {
            valid = false;
            firstName.setError("Please enter your first name.");
        }

        if (lastName.getText().toString().isEmpty()) {
            valid = false;
            lastName.setError("Please enter your last name.");
        }

        if (email.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            valid = false;
            email.setError("Please enter a valid e-mail address.");
        }

        return valid;
    }

    private void updateUser(View view, String firstName, String lastName, String email) {
        String username = LoggedUser.getUsername();

        UpdatePatch updatePatch = new UpdatePatch(username, email, firstName, lastName);

        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);

        Call<UpdatePatch> update = herokuAPI.updateData(username, updatePatch);

        update.enqueue(new Callback<UpdatePatch>() {
            @Override
            public void onResponse(@NotNull Call<UpdatePatch> call, @NotNull Response<UpdatePatch> response) {
                // Hiding the waiting GIF
                loadingImageView.setVisibility(View.GONE);

                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/update", "notSuccessful: Something went wrong. " + response.code());
                    return;
                }

                UpdatePatch updatePatchResponse = response.body();

                assert updatePatchResponse != null;
                if ("user_updated".equals(updatePatchResponse.getFeedback())) {
                    Toast.makeText(view.getContext(), "Update successful.", Toast.LENGTH_LONG).show();
                    setLoggedUser(view, username);
                } else {// Possible database error server-side, user not found...
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdatePatch> call, @NotNull Throwable t) {
                Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                Log.e("/update", "onFailure: Something went wrong. " + t.getMessage());
            }
        });
    }

    private void setLoggedUser(View view, String username) {
        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);
        Call<User> call = herokuAPI.getUser(username);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/getUsers", "notSuccessful: Something went wrong. " + response.code());
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                    return;
                }

                assert response.body() != null;
                User user = response.body();

                if (user.getFeedback().equals("user_found")) {
                    LoggedUser.setData(user);
                    LoggedUser.setIsLoggedIn(true);

                    TextView fullName = (TextView) view.findViewById(R.id.fullNameTextView);
                    TextView labelUsername = (TextView) view.findViewById(R.id.usernameTextView);
                    TextInputEditText firstName = (TextInputEditText) view.findViewById(R.id.firstNameTextInputEditText);
                    TextInputEditText lastName = (TextInputEditText) view.findViewById(R.id.lastNameTextInputEditText);
                    TextInputEditText email = (TextInputEditText) view.findViewById(R.id.emailTextInputEditText);

                    NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_view);
                    View headerView = navigationView.getHeaderView(0);
                    TextView navigationLabelFullName = (TextView) headerView.findViewById(R.id.navigation_full_name);
                    TextView navigationLabelUsername = (TextView) headerView.findViewById(R.id.navigation_username);

                    fullName.setText(String.format("%s %s", LoggedUser.getFirstName(), LoggedUser.getLastName()));
                    navigationLabelFullName.setText(String.format("%s %s", LoggedUser.getFirstName(), LoggedUser.getLastName()));
                    labelUsername.setText(LoggedUser.getUsername());
                    navigationLabelUsername.setText(LoggedUser.getUsername());
                    firstName.setText(LoggedUser.getFirstName());
                    lastName.setText(LoggedUser.getLastName());
                    email.setText(LoggedUser.getEmail());
                } else {
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                // Communication error, JSON parsing error, class configuration error...
                Log.e("/getUsers", "onFailure: Something went wrong. " + t.getMessage());
            }
        });
    }
}