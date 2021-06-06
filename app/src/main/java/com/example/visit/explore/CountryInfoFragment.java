package com.example.visit.explore;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.visit.R;

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
    private TextView countryDesc;

    private Spinner dropdown;
    private View calculatorView;
    private Button calculateButton;
    private TextView result;


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

        calculatorView = view.findViewById(R.id.bigmac_calculator_view);
        dropdown = calculatorView.findViewById(R.id.country_of_origin_dropdown);
        calculateButton = calculatorView.findViewById(R.id.calculate_button);
        result = calculatorView.findViewById(R.id.calculator_result);

        ArrayList<String> countryDropdownItems = new ArrayList<>();

        for(CountryModel c : countryModelList) {
            countryDropdownItems.add(countryModelList.get(c.getCountry_id() - 1).getCountry_name());
        }

        String[] countryArray = countryDropdownItems.toArray(new String[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countryArray);
        dropdown.setAdapter(adapter);


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
        countryDesc = countryInfoView.findViewById(R.id.country_info_description);

        // Filling the elements with data stored in the CountryModel
        Glide.with(getContext()).asBitmap().load(country.getGeolocation()).into(countryGeolocation);
        Glide.with(getContext()).asBitmap().load(country.getCountry_flag()).into(countryFlag);
        countryCurrency.setText("Currency: " + country.getCountry_currency());
        countryPopulation.setText("Population: " + formatter.format(country.getCountry_pop()));
        countryLanguage.setText("Language: " + country.getLanguage_top());
        countryCapital.setText("Capital: " + country.getCapital_city());
        localTime.setTimeZone(country.getCountry_timezone());
        countryDesc.setText(country.getCountry_desc());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double bigmacRatio = countryModelList.get(dropdown.getSelectedItemPosition()).getBigmac_index() / country.getBigmac_index();
                String ratioColor;

                if(bigmacRatio >= 1) {
                    ratioColor = "green";
                } else ratioColor = "red";

                String resultText = "With <font color=#1AABFF> $100 in " + country.getCountry_name() + "</font> you would have the same purchasing power as with <font color=" + ratioColor + ">$" +
                        String.format("%.2f", bigmacRatio * 100)  + " in " + dropdown.getSelectedItem().toString() + "</font> according to the Big Mac Index.";
                result.setText(Html.fromHtml(resultText));
            }
        });
    }
}