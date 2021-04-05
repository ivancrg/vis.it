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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.visit.database.Database;
import com.example.visit.database.HerokuAPI;
import com.example.visit.database.LoginPost;
import com.example.visit.database.Users;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText username = (EditText) view.findViewById(R.id.loginFragmentUsername);
        final EditText password = (EditText) view.findViewById(R.id.loginFragmentPassword);
        final Button login = (Button) view.findViewById(R.id.loginFragmentButton);
        final TextView registerLink = (TextView) view.findViewById(R.id.loginFragmentRegisterLink);

        login.setEnabled(false);

        TextWatcher loginEnabledWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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

            username.setText("");
            password.setText("");
        });

        registerLink.setOnClickListener(view12 -> {
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentRegister, new RegisterFragment());
            fragmentTransaction.commit();
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
                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/login", "notSuccessful: Something went wrong. " + response.code());
                    return;
                }

                LoginPost postResponse = response.body();
                //Toast.makeText(view.getContext(), response.body().toString(), Toast.LENGTH_LONG).show();

                assert postResponse != null;
                if (postResponse.getFeedback().equals("database_error")) {
                    // Database error server-side
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                } else if (postResponse.getFeedback().equals("user_not_found")) {
                    // User not found
                    Toast.makeText(view.getContext(), "Check your login details.", Toast.LENGTH_LONG).show();
                } else if (!postResponse.getPassword().equals(password)) {
                    // Incorrect password, user found
                    Toast.makeText(view.getContext(), "Check your login details.", Toast.LENGTH_LONG).show();
                } else if (postResponse.getPassword().equals(password) && postResponse.getFeedback().equals("user_found")) {
                    // Correct password, user found
                    Toast.makeText(view.getContext(), "Welcome, " + username + "!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginPost> call, @NotNull Throwable t) {
                Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                Log.e("/login", "onFailure: Something went wrong. " + t.getMessage());
            }
        });
    }

    private void getUsers(View view) {
        Retrofit retrofit = Database.getRetrofit();

        // Retrofit takes care of interface implementation
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);

        // Retrofit implements getMembers() method set in HerokuAPI interface
        Call<List<Users>> call = herokuAPI.getUsers();

        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(@NotNull Call<List<Users>> call, @NotNull Response<List<Users>> response) {
                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/getUsers", "notSuccessful: Something went wrong. " + response.code());
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                    return;
                }

                assert response.body() != null;
                Toast.makeText(view.getContext(), response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NotNull Call<List<Users>> call, @NotNull Throwable t) {
                // Communication error, JSON parsing error, class configuration error...
                Log.e("/getUsers", "onFailure: Something went wrong. " + t.getMessage());
            }
        });
    }
}