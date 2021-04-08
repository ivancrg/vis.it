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
import com.example.visit.database.UpdatePatch;
import com.example.visit.database.Users;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserInterfaceFragment extends Fragment {

    public UserInterfaceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_interface , container, false);

        TextView fullName = (TextView) view.findViewById(R.id.fullNameTextView);
        TextView labelUsername = (TextView) view.findViewById(R.id.usernameTextView);
        TextInputEditText firstName = (TextInputEditText) view.findViewById(R.id.firstNameTextInputEditText);
        TextInputEditText lastName = (TextInputEditText) view.findViewById(R.id.lastNameTextInputEditText);
        TextInputEditText username = (TextInputEditText) view.findViewById(R.id.usernameTextInputEditText);
        TextInputEditText email = (TextInputEditText) view.findViewById(R.id.emailTextInputEditText);
        Button update = (Button) view.findViewById(R.id.updateButton);
        Button changePassword = (Button) view.findViewById(R.id.changePasswordButton);

        getUsers(view);
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
                        email.getText().length() > 0 &&
                        username.getText().length() > 0);
            }
        };

        firstName.addTextChangedListener(updateEnableWatcher);
        lastName.addTextChangedListener(updateEnableWatcher);
        email.addTextChangedListener(updateEnableWatcher);
        username.addTextChangedListener(updateEnableWatcher);

        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (informationValid(firstName.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString(),
                        username.getText().toString())) {
                    //update method
                    UpdatePatch updatePatch = new UpdatePatch(username.getText().toString(),email.getText().toString(),
                            firstName.getText().toString(),lastName.getText().toString());

                    Retrofit retrofit = Database.getRetrofit();
                    HerokuAPI herokuAPI = retrofit.create(HerokuAPI.class);

                    Call<UpdatePatch> update = herokuAPI.updateData(username.getText().toString(), updatePatch);

                    update.enqueue(new Callback<UpdatePatch>() {
                        @Override
                        public void onResponse(@NotNull Call<UpdatePatch> call, @NotNull Response<UpdatePatch> response) {
                            if (!response.isSuccessful()) {
                                // Not OK
                                Log.e("/update", "notSuccessful: Something went wrong. " + response.code());
                                return;
                            }

                            UpdatePatch postResponse = response.body();
                            //Toast.makeText(view.getContext(), response.body().toString(), Toast.LENGTH_LONG).show();

                            assert postResponse != null;
                            if (postResponse.getFeedback().equals("database_error")) {
                                // Database error server-side
                                Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                            } else if (postResponse.getFeedback().equals("user_not_found")) {
                                // User not found
                                Toast.makeText(view.getContext(), "Check your username", Toast.LENGTH_LONG).show();

                            } else if (postResponse.getFeedback().equals("user_found")) {
                                // Correct password, user found
                                Toast.makeText(view.getContext(), "Update successful " + username, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<UpdatePatch> call, @NotNull Throwable t) {
                            Toast.makeText(view.getContext(), "Sorry, there was an error.", Toast.LENGTH_LONG).show();
                            Log.e("/update", "onFailure: Something went wrong. " + t.getMessage());
                        }
                    });
                }
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLogin, new ChangePasswordFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }
    private boolean informationValid(String firstName, String lastName, String email, String username) {
            // TODO CHECK INFO
            // Setting error example
            //firstName.setError("You need to enter a name");

            return true;
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
                    for(Users user : response.body())
                    {
                        if (user.getUsername().equals(LoggedUser.getUsername()))
                        {
                            TextView fullName = view.findViewById(R.id.fullNameTextView);
                            TextView labelUsername = view.findViewById(R.id.usernameTextView);
                            TextView firstName = view.findViewById(R.id.firstNameTextInputEditText);
                            TextView lastName = view.findViewById(R.id.lastNameTextInputEditText);
                            TextView username = view.findViewById(R.id.usernameTextInputEditText);
                            TextView email = view.findViewById(R.id.emailTextInputEditText);
                            fullName.setText(user.getFirstName() + " " + user.getLastName());
                            labelUsername.setText(user.getUsername());
                            firstName.setText(user.getFirstName());
                            lastName.setText(user.getLastName());
                            username.setText(user.getUsername());
                            email.setText(user.getEmail());
                            break;
                        }
                    }
                }

            @Override
            public void onFailure(@NotNull Call<List<Users>> call, @NotNull Throwable t) {
                // Communication error, JSON parsing error, class configuration error...
                Log.e("/getUsers", "onFailure: Something went wrong. " + t.getMessage());
            }
        });
    }

}