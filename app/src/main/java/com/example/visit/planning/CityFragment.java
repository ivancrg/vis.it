package com.example.visit.planning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.visit.MainActivity;
import com.example.visit.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CityFragment extends Fragment {

    public CityFragment() {
        // Required empty public constructor
    }

    public static CityFragment newInstance() {
        return new CityFragment();
    }

    View view;
    Spinner spinner;
    List<String> cityList;
    ArrayAdapter<String> catAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_city, container, false);

        TextInputEditText city = (TextInputEditText) view.findViewById(R.id.cityEdit);
        Button next = (Button) view.findViewById(R.id.next_city);
        Button cancel = (Button) view.findViewById(R.id.cancel_city);
        TextView continueExploring = (TextView) view.findViewById(R.id.continue_text);

        // data is chosen country
        Bundle args = this.getArguments();
        String data = args.getString("key");

        if (data == null) {
            // country is not chosen
            data = "";
            Toast.makeText(view.getContext(), "Please choose destination country first!", Toast.LENGTH_LONG).show();
            next.setEnabled(false);
            cancel.setText("Go back");
        } else {
            next.setEnabled(true);
            cancel.setText("Cancel");
        }

        spinner = new Spinner(this.getContext());
        spinner = view.findViewById(R.id.spinner);
        cityList = new ArrayList<String>();
        catAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, cityList);

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        // requesting cities only from chosen country
        RequestBody body = RequestBody.create(mediaType, "{\r\n   \"country\": \"" + data + "\"\r\n}");
        Request request = new Request.Builder()
                .url("https://countriesnow.space/api/v0.1/countries/cities")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "__cfduid=db014f690fb26fc59e5c50092c509f28b1619124682")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject cities = new JSONObject(myResponse);
                                JSONArray cityArr = cities.getJSONArray("data");
                                // creating list of cities
                                ArrayList<String> list = new ArrayList<String>();
                                for (int i = 0; i < cityArr.length(); i++) {
                                    list.add(cityArr.getString(i));
                                }
                                ArrayAdapter<String> spinnerMenu = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
                                spinnerMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(spinnerMenu);
                                catAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), "Country not available, please pick another country!", Toast.LENGTH_SHORT).show();
                            MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new CountryFragment(), false);
                        }
                    });
                }
            }
        });

        if (TripPlanning.getCity() != null) {
            city.setText(TripPlanning.getCity());
        }

        next.setOnClickListener(view -> {
            //destination string holds the city user picked
            String cityString = spinner.getSelectedItem().toString();
            if (cityString.length() > 0) {
                TripPlanning.setCity(cityString);
                MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new AccommodationFragment(), true);
            } else {
                Toast.makeText(view.getContext(), "Choose destination city!", Toast.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(view -> {
            MainActivity.changeFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new TripPlannerFragment(), true);
        });

        continueExploring.setOnClickListener(view -> {
            // TODO
            //needs to be forwarded to Explore fragment
        });

        return view;
    }
}