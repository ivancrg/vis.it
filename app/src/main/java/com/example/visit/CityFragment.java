package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class CityFragment extends Fragment {

    Button city;
    View view;

    public CityFragment() {
        // Required empty public constructor
    }
/*
    public static CityFragment newInstance() {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        return fragment;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_city, container, false);

        return view;
    }
/*
    public void onViewCreated(View view, Bundle savedInstanceStat){
        city = view.findViewById(R.id.save_city);
        TextView postText = view.findViewById(R.id.postText);
        postText.setText("Nesto rendom");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://countriesnow.space/api/v0.1/countries")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CountryCityAPI countryCityAPI = retrofit.create(CountryCityAPI.class);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

}