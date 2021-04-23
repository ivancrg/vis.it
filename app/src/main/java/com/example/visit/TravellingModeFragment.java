package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class TravellingModeFragment extends Fragment {

    public TravellingModeFragment() {
        // Required empty public constructor
    }

    public static TravellingModeFragment newInstance() {
        TravellingModeFragment fragment = new TravellingModeFragment();
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