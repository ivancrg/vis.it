package com.example.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class CountryInfoFragment extends Fragment {
    private CountryModel country;
    private ArrayList<CountryModel> countryModelList;

    private View titleElement;
    private TextView countryTitle;
    private ImageView countryImage;

    private View countryInfoView;
    private ImageView countryFlag;
    private ImageView countryGeolocation;
    private TextView countryCurrency;
    private TextView countryPopulation;
    private TextView countryLanguage;
    private TextView countryCapital;
    private TextClock localTime;


    public CountryInfoFragment (CountryModel country, ArrayList<CountryModel> countryModelList) {
        this.country = country;
        this.countryModelList = countryModelList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_info, container, false);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        titleElement = view.findViewById(R.id.country_info_title_element);
        countryTitle = titleElement.findViewById(R.id.country_info_title);
        countryImage = titleElement.findViewById(R.id.country_info_country_image);

        countryTitle.setText("Explore " + country.getCountry_name());
        Glide.with(getContext()).asBitmap().load(country.getCountry_image()).into(countryImage);

        countryInfoView = view.findViewById(R.id.country_info_element);
        countryGeolocation = view.findViewById(R.id.country_info_geolocation);
        countryFlag = countryInfoView.findViewById(R.id.country_info_flag);
        countryCurrency = countryInfoView.findViewById(R.id.country_info_currency);
        countryPopulation = countryInfoView.findViewById(R.id.country_info_population);
        countryCapital = countryInfoView.findViewById(R.id.country_info_captial_city);
        countryLanguage = countryInfoView.findViewById(R.id.country_info_language);
        localTime = countryInfoView.findViewById(R.id.country_info_local_time_clock);

        // Filling the elements with data stored in the CountryModel
        Glide.with(getContext()).asBitmap().load(country.getGeolocation()).into(countryGeolocation);
        Glide.with(getContext()).asBitmap().load(country.getCountry_flag()).into(countryFlag);
        countryCurrency.setText("Currency: " + country.getCountry_currency());
        countryPopulation.setText("Population: " + formatter.format(country.getCountry_pop()));
        countryLanguage.setText("Language: " + country.getLanguage_top());
        countryCapital.setText("Capital: " + country.getCapital_city());
        localTime.setTimeZone(country.getCountry_timezone());

        return view;
    }
}