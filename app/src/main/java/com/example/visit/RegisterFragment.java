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
import com.example.visit.database.RegisterPost;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import org.springframework.security.crypto.bcrypt.BCrypt;


public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        final EditText firstName = (EditText) view.findViewById(R.id.registerFragmentFirstName);
        final EditText lastName = (EditText) view.findViewById(R.id.registerFragmentLastName);
        final EditText email = (EditText) view.findViewById(R.id.registerFragmentEmail);
        final EditText username = (EditText) view.findViewById(R.id.registerFragmentUsername);
        final EditText password = (EditText) view.findViewById(R.id.registerFragmentPassword);
        final EditText passwordConfirm = (EditText) view.findViewById(R.id.registerFragmentPasswordConfirm);

        final Button register = (Button) view.findViewById(R.id.registerFragmentButton);

        register.setEnabled(false);

        TextWatcher registerEnableWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                register.setEnabled(firstName.getText().length() > 0 &&
                        lastName.getText().length() > 0 &&
                        email.getText().length() > 0 &&
                        username.getText().length() > 0 &&
                        password.getText().length() > 0 &&
                        passwordConfirm.getText().length() > 0 &&
                        password.getText().toString().equals(passwordConfirm.getText().toString()));
            }
        };

        firstName.addTextChangedListener(registerEnableWatcher);
        lastName.addTextChangedListener(registerEnableWatcher);
        email.addTextChangedListener(registerEnableWatcher);
        username.addTextChangedListener(registerEnableWatcher);
        password.addTextChangedListener(registerEnableWatcher);
        passwordConfirm.addTextChangedListener(registerEnableWatcher);

        register.setOnClickListener(view1 -> {
            if (informationValid(firstName.getText().toString(),
                    lastName.getText().toString(),
                    email.getText().toString(),
                    username.getText().toString(),
                    password.getText().toString())) {
                registerUser(view1,
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString(),
                        username.getText().toString(),
                        password.getText().toString());
            }

            firstName.setText("");
            lastName.setText("");
            email.setText("");
            username.setText("");
            password.setText("");
            passwordConfirm.setText("");
        });

        return view;
    }

    private boolean informationValid(String firstName, String lastName, String email, String username, String password) {
        // TODO CHECK INFO
        // Setting error example
        //firstName.setError("You need to enter a name");

        return true;
    }

    private void registerUser(View view, String firstName, String lastName, String email, String username, String password) {
        // Hashing variable password and storing it to passwordHashed
        String passwordHashed = BCrypt.hashpw(password, BCrypt.gensalt());

        RegisterPost registerPost = new RegisterPost(username, email, passwordHashed, firstName, lastName);

        Retrofit retrofit = Database.getRetrofit();
        HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);

        Call<RegisterPost> call = herokuAPI.register(registerPost);

        call.enqueue(new Callback<RegisterPost>() {
            @Override
            public void onResponse(@NotNull Call<RegisterPost> call, @NotNull Response<RegisterPost> response) {
                if (!response.isSuccessful()) {
                    // Not OK
                    Log.e("/register", "notSuccessful: Something went wrong. " + response.code());
                    Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                    return;
                }

                RegisterPost postResponse = response.body();

                assert postResponse != null;
                switch (postResponse.getFeedback()) {
                    case "database_error":
                        // Database error server-side
                        Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                        break;
                    case "username_unavailable":
                        // Username not available
                        Toast.makeText(view.getContext(), "Sorry, this username is already taken.", Toast.LENGTH_LONG).show();
                        break;
                    case "user_registered":
                        // User is registered
                        Toast.makeText(view.getContext(), "Congratulations, you have been successfully registered.", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(@NotNull Call<RegisterPost> call, @NotNull Throwable t) {
                Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                Log.e("/register", "onFailure: Something went wrong. " + t.getMessage());
            }
        });
    }
}