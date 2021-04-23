package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class TravellingNecessitiesFragment extends Fragment {

    public TravellingNecessitiesFragment() {
        // Required empty public constructor
    }
    public static TravellingNecessitiesFragment newInstance() {
        TravellingNecessitiesFragment fragment = new TravellingNecessitiesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_travelling_necessities, container, false);


        Button button = view.findViewById(R.id.save_necessities);
        TextView continue_exploring = view.findViewById(R.id.continue_text);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //String with the necessities
                String necessities = view.findViewById(R.id.list_necessities).toString();

            }
        });

        return view;
    }
}