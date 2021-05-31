package com.example.visit.user_profile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.visit.MainActivity;
import com.example.visit.R;
import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;
import com.example.visit.database.LoginPost;
import com.example.visit.database.User;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {
    private EditText usernameEditText;
    private EditText passwordEditText;

    // GifImageView for GIF that shows up while waiting for API to respond
    private pl.droidsonroids.gif.GifImageView loadingImageView;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        if (LoggedUser.getIsLoggedIn()) {
            Toast.makeText(view.getContext(), "Welcome, " + LoggedUser.getUsername(), Toast.LENGTH_LONG).show();
            MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), new UserInterfaceFragment(), false);
        }

        final EditText username = (EditText) view.findViewById(R.id.loginFragmentUsername);
        final EditText password = (EditText) view.findViewById(R.id.loginFragmentPassword);
        final Button login = (Button) view.findViewById(R.id.loginFragmentButton);
        final TextView registerLink = (TextView) view.findViewById(R.id.loginFragmentRegisterLink);

        // LoginFragment.java level variables - to enable access inside of loginUser method
        usernameEditText = username;
        passwordEditText = password;
        loadingImageView = (pl.droidsonroids.gif.GifImageView) view.findViewById(R.id.loginFragmentLoading);

        login.setEnabled(false);

        TextWatcher loginEnabledWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // we don't use this
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // we don't use this
            }

            @Override
            public void afterTextChanged(Editable editable) {
                login.setEnabled(username.getText().length() > 0 && password.getText().length() > 0);
            }
        };

        username.addTextChangedListener(loginEnabledWatcher);
        password.addTextChangedListener(loginEnabledWatcher);

        login.setOnClickListener(view1 -> {
            loginUser(view1, username.getText().toString(), password.getText().toString());
            // Disabling input of username and password fields, showing the waiting GIF
            usernameEditText.setEnabled(false);
            passwordEditText.setEnabled(false);
            loadingImageView.setVisibility(View.VISIBLE);
        });

        registerLink.setOnClickListener(view12 -> {
            MainActivity.changeFragment(requireActivity().getSupportFragmentManager(), new RegisterFragment(), true);
        });

        return view;
    }

    private void loginUser(View view, String username, String password) {
        LoginPost loginPost = new LoginPost(username);

        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);

        Call<LoginPost> call = herokuAPI.login(loginPost);

        call.enqueue(new Callback<LoginPost>() {
            @Override
            public void onResponse(@NotNull Call<LoginPost> call, @NotNull Response<LoginPost> response) {
                // Enabling input to username and password fields and removing loading GIF
                usernameEditText.setEnabled(true);
                passwordEditText.setEnabled(true);
                loadingImageView.setVisibility(View.GONE);

                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/login", "notSuccessful: Something went wrong. " + response.code());
                    return;
                }

                LoginPost postResponse = response.body();

                assert postResponse != null;
                if (postResponse.getFeedback().equals("database_error")) {
                    // Database error server-side
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                } else if (postResponse.getFeedback().equals("user_not_found")) {
                    // User not found
                    Toast.makeText(view.getContext(), "Check your login details.", Toast.LENGTH_LONG).show();
                } else {
                    boolean loggedIn = false;

                    try {
                        // Checks whether inserted password corresponds to the password in the database
                        // true --> correct password
                        // false --> incorrect password

                        loggedIn = BCrypt.checkpw(password, postResponse.getPassword());
                    } catch (Exception e) {
                        Toast.makeText(view.getContext(), "Exception. Sorry. " + e.getMessage() + ".", Toast.LENGTH_LONG).show();
                    }

                    // If statements intentionally left out of try block to give user the feedback whatsoever
                    if (!loggedIn) {
                        // Incorrect password, user found
                        Toast.makeText(view.getContext(), "Check your login details.", Toast.LENGTH_LONG).show();
                    } else if (loggedIn && postResponse.getFeedback().equals("user_found")) {
                        // Correct password, user found
                        usernameEditText.setText("");
                        passwordEditText.setText("");
                        Toast.makeText(view.getContext(), "Welcome, " + username, Toast.LENGTH_LONG).show();
                        setLoggedUser(view, username);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginPost> call, @NotNull Throwable t) {
                loadingImageView.setVisibility(View.GONE);
                Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                Log.e("/login", "onFailure: Something went wrong. " + t.getMessage());
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
                    Log.e("/getUser", "notSuccessful: Something went wrong. " + response.code());
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                    return;
                }

                assert response.body() != null;

                User user = response.body();

                if (user.getFeedback().equals("user_found")) {
                    LoggedUser.setData(user);
                    LoggedUser.setIsLoggedIn(true);

                    MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new UserInterfaceFragment(), false);
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