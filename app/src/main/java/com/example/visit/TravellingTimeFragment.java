package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

public class TravellingTimeFragment extends Fragment {

    public TravellingTimeFragment() {
        // Required empty public constructor
    }

    public static TravellingTimeFragment newInstance() {
        TravellingTimeFragment fragment = new TravellingTimeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_travelling_mode, container, false);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}