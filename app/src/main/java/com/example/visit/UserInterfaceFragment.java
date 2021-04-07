package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

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

        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            //update method
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentUserInterface, new ChangePasswordFragment());
                fragmentTransaction.commit();
            }
        });
        //getUsers(view);
        return view;
    }


    /*private void getUsers(View view) {
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
                            view.findViewById(R.id.fullNameTextView).setText(user.getFirstame() + " " + user.getLastname);
                            view.findViewById(R.id.usernameTextView).setText(user.getUsername());
                            view.findViewById(R.id.firstNameTextInputEditText).setText(user.getFirstname());
                            view.findViewById(R.id.lastNameTextInputEditText).setText(user.getLastname());
                            view.findViewById(R.id.usernameTextInputEditText).setText(user.getUsername());
                            view.findViewById(R.id.emailTextInputEditText).setText(user.getEmail());
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
    }*/

}