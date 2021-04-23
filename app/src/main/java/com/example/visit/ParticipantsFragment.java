package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class ParticipantsFragment extends Fragment {

    public ParticipantsFragment() {
        // Required empty public constructor
    }

    public static ParticipantsFragment newInstance() {
        ParticipantsFragment fragment = new ParticipantsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_participants, container, false);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}