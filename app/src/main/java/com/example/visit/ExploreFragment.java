package com.example.visit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.visit.recyclerView.CountryRecyclerViewAdapter;
import com.example.visit.recyclerView.CountryRecyclerViewItem;
import com.example.visit.recyclerView.HorizontalRecyclerViewAdapter;
import com.example.visit.recyclerView.HorizontalRecyclerViewItem;
import com.example.visit.recyclerView.RecyclerViewClickInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class ExploreFragment extends Fragment implements RecyclerViewClickInterface {
    private RecyclerView cityView;
    private RecyclerView summerView;
    private RecyclerView winterView;
    private RecyclerView countryView;

    private static final int NUM_OF_RECYCLER_ITEMS = 5;

    // Adapter is a sort of a bridge between our data (verticalItems) and the RV
    // Adapter always provides as many items as we need at the time which means optimal performance
    private RecyclerView.Adapter cityAdapter;
    private RecyclerView.Adapter summerAdapter;
    private RecyclerView.Adapter winterAdapter;
    private RecyclerView.Adapter countryAdapter;


    // Responsible for placing items into our list
    private RecyclerView.LayoutManager cityLayoutManager;
    private RecyclerView.LayoutManager summerLayoutManager;
    private RecyclerView.LayoutManager winterLayoutManager;
    private RecyclerView.LayoutManager countryLayoutManager;


    View travelTipsArticle;
    ImageButton articleButtonTop;
    TextView articleTitleTop;
    TextView articleSubtitleTop;

    View genericArticle;
    ImageButton articleButtonBottom;
    TextView articleTitleBottom;
    TextView articleSubtitleBottom;

    View countryElement;
    TextView countryCategoryTitle;
    TextView countryCategoryText;

    View cityElement;
    TextView cityCategoryTitle;
    TextView cityCategoryText;

    View summerElement;
    TextView summerCategoryTitle;
    TextView summerCategorySubtitle;

    View winterElement;
    TextView winterCategoryTitle;
    TextView winterCategorySubtitle;

    // Recycler View items lists
    ArrayList<ArticleModel> articleList;
    ArrayList<CountryModel> countryList;
    ArrayList<CityModel> cityList;
    ArrayList<SummerAndWinterModel> summerList;
    ArrayList<SummerAndWinterModel> winterList;

    ArrayList<CountryRecyclerViewItem> countriesList;
    ArrayList<HorizontalRecyclerViewItem> citiesList;
    ArrayList<HorizontalRecyclerViewItem> summerItemList;
    ArrayList<HorizontalRecyclerViewItem> winterItemList;


    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleList = new ArrayList<>();
        countryList = new ArrayList<>();
        cityList = new ArrayList<>();
        countriesList = new ArrayList<>();
        citiesList = new ArrayList<>();
        winterList = new ArrayList<>();
        summerList = new ArrayList<>();
        summerItemList = new ArrayList<>();
        winterItemList = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        setArticles();
        setCountries();
        setCities();
        setSummerAndWinter();

        // Vertical is used to represent horizontal recycler view items

        cityElement = view.findViewById(R.id.explore_cities_item);
        cityCategoryTitle = cityElement.findViewById(R.id.verticalRecyclerViewItemTitle);
        cityCategoryText = cityElement.findViewById(R.id.verticalRecyclerViewItemText);
        cityCategoryTitle.setText(R.string.cityCategoryTitle);
        cityCategoryText.setText(R.string.cityCategorySubtitle);

        countryElement = view.findViewById(R.id.explore_countries_item);
        countryCategoryTitle = countryElement.findViewById(R.id.verticalRecyclerViewItemTitle);
        countryCategoryText = countryElement.findViewById(R.id.verticalRecyclerViewItemText);
        countryCategoryTitle.setText(R.string.countryCategoryTitle);
        countryCategoryText.setText(R.string.countryCategorySubtitle);

        summerElement = view.findViewById(R.id.explore_summer_item);
        summerCategoryTitle = summerElement.findViewById(R.id.verticalRecyclerViewItemTitle);
        summerCategorySubtitle = summerElement.findViewById(R.id.verticalRecyclerViewItemText);
        summerCategoryTitle.setText(R.string.summerCategoryTitle);
        summerCategorySubtitle.setText(R.string.summerCategorySubtitle);

        winterElement = view.findViewById(R.id.explore_winter_item);
        winterCategoryTitle = winterElement.findViewById(R.id.verticalRecyclerViewItemTitle);
        winterCategorySubtitle = winterElement.findViewById(R.id.verticalRecyclerViewItemText);
        winterCategoryTitle.setText(R.string.winterCategoryTitle);
        winterCategorySubtitle.setText(R.string.winterCategorySubtitle);

        cityView = cityElement.findViewById(R.id.horizontalRecyclerView);
        countryView = countryElement.findViewById(R.id.horizontalRecyclerView);
        summerView = summerElement.findViewById(R.id.horizontalRecyclerView);
        winterView = winterElement.findViewById(R.id.horizontalRecyclerView);

        // Needs to be set to true if recycler view won't change in size for performance gains
        cityView.setHasFixedSize(true);
        countryView.setHasFixedSize(true);
        summerView.setHasFixedSize(true);
        winterView.setHasFixedSize(true);


        cityLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cityAdapter = new HorizontalRecyclerViewAdapter(citiesList, getContext());
        cityView.setLayoutManager(cityLayoutManager);
        cityView.setAdapter(cityAdapter);

        countryLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        countryAdapter = new CountryRecyclerViewAdapter(countriesList, getContext(), this);
        countryView.setLayoutManager(countryLayoutManager);
        countryView.setAdapter(countryAdapter);

        summerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        summerAdapter = new HorizontalRecyclerViewAdapter(summerItemList, getContext());
        summerView.setLayoutManager(summerLayoutManager);
        summerView.setAdapter(summerAdapter);

        winterLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        winterAdapter = new HorizontalRecyclerViewAdapter(winterItemList, getContext());
        winterView.setLayoutManager(winterLayoutManager);
        winterView.setAdapter(winterAdapter);


        //Filling the articles with data
        travelTipsArticle = view.findViewById(R.id.travel_tips_article);
        articleButtonTop = travelTipsArticle.findViewById(R.id.article_background_button);
        articleTitleTop = travelTipsArticle.findViewById(R.id.article_title);
        articleSubtitleTop = travelTipsArticle.findViewById(R.id.article_subtitle);

        genericArticle = view.findViewById(R.id.generic_travel_article);
        articleButtonBottom = genericArticle.findViewById(R.id.article_background_button);
        articleTitleBottom = genericArticle.findViewById(R.id.article_title);
        articleSubtitleBottom = genericArticle.findViewById(R.id.article_subtitle);

        return view;
    }

    // OnClick method for articles
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        articleButtonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!articleList.isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleList.get(0).getArticleUrl()));
                    startActivity(browserIntent);

                    Log.d("BUTTON", "ARTICLE 1 CLICKED");
                }
            }
        });

        articleButtonBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!articleList.isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleList.get(1).getArticleUrl()));
                    startActivity(browserIntent);

                    Log.d("BUTTON", "ARTICLE 2 CLICKED");
                }
            }
        });
    }

    // Filling the countries recycle viewer
    private void setCountries() {

        // Php file which returns the country data
        final String URL_COUNTRIES = "https://visitcountryapi.000webhostapp.com/exploreCountries.php";

        // Sending the data request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_COUNTRIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d("RESPONSE", "Response COUNTRY success " + response);

                    // Randomly picking countries to display
                    Vector<Integer> countryIndexVector = new Vector<Integer>();
                    countryIndexVector = randomNumGen(0 , array.length(), NUM_OF_RECYCLER_ITEMS);

                    Log.d("COUNTRY", "Country index" + countryIndexVector);

                    // Creating objects for the country data
                    for(int i = 0; i < array.length(); i++) {
                        JSONObject countryObject = array.getJSONObject(i);
                        countryList.add(new CountryModel(countryObject.getInt("country_id"),
                                countryObject.getString("country_name"),
                                countryObject.getString("country_code"),
                                countryObject.getString("country_flag").replace("\\",""),
                                countryObject.getString("country_currency"),
                                countryObject.getInt("country_pop"),
                                countryObject.getString("country_image").replace("\\", ""),
                                countryObject.getDouble("bigmac_index"),
                                countryObject.getString("country_desc"),
                                countryObject.getString("country_hemisphere"),
                                countryObject.getString("language_top"),
                                countryObject.getString("capital_city"),
                                countryObject.getString("country_timezone"),
                                countryObject.getString("call_code"),
                                "https://visitcountryapi.000webhostapp.com/Geolocation/" + countryObject.getInt("country_id") + ".png"));

                        Log.d("COUNTRY POPULATION", countryObject.getString("country_name") + " POP: " + countryObject.getInt("country_pop"));
                    }

                    for(int ind : countryIndexVector) {
                        countriesList.add(new CountryRecyclerViewItem(countryList.get(ind).getCountry_image(), countryList.get(ind).getCountry_flag(),
                                countryList.get(ind).getCountry_name(), "", countryList.get(ind).getCountry_id()));
                    }
                    Log.d("CHANGED SET", "Notify dataset changed");
                    countryAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    // Filling the article CardView
    private void setArticles() {
        // Php file that returns the data
        final String URL_ARTICLES = "https://visitcountryapi.000webhostapp.com/exploreArticles.php";

        Log.d("RESPONSE ARTICLES", "Request started");

        // Sending the data request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ARTICLES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d("RESPONSE", "Response ARTICLE success " + response);

                    // Randomly choosing two articles to display
                    Vector<Integer> articleIndexVector = new Vector<Integer>();
                    articleIndexVector = randomNumGen(0 , array.length(), 2);

                    Log.d("RESPONSE", "Article index" + articleIndexVector);

                    for(int ind : articleIndexVector) {
                        JSONObject articleObject = array.getJSONObject(ind);
                        articleList.add(new ArticleModel(ind,articleObject.getString("title"),
                                articleObject.getString("subtitle"),
                                articleObject.getString("link"),
                                articleObject.getString("image_url").replace("\\",""),
                                articleObject.getString("type")));

                        Log.d("TITLE", articleObject.getString("title"));
                    }

                    Log.d("RESPONSE ARTICLE", Boolean.toString(articleList.isEmpty()));

                    fillData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    // Filling the article elements with data
    private void fillData() {
        if(!articleList.isEmpty()) {
            articleTitleTop.setText(articleList.get(0).getTitle());
            articleSubtitleTop.setText(articleList.get(0).getSubtitle());
            Glide.with(getContext()).load(articleList.get(0).getImageUrl()).centerCrop().into(articleButtonTop);

            articleTitleBottom.setText(articleList.get(1).getTitle());
            articleSubtitleBottom.setText(articleList.get(1).getSubtitle());
            Glide.with(getContext()).load(articleList.get(1).getImageUrl()).centerCrop().into(articleButtonBottom);
        }
    }

    private void setCities() {
        // Php file that returns the city data
        final String URL_CITIES = "https://visitcountryapi.000webhostapp.com/exploreCities.php";

        // Sending the data request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CITIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d("RESPONSE", "Response CITY success " + response);

                    // Selecting random cities to display
                    Vector<Integer> cityIndexVector = new Vector<Integer>();
                    cityIndexVector = randomNumGen(0 , array.length(), NUM_OF_RECYCLER_ITEMS);

                    Log.d("CITY", "City index" + cityIndexVector);

                    for(int ind : cityIndexVector) {
                        JSONObject cityObject = array.getJSONObject(ind);
                        cityList.add(new CityModel(cityObject.getInt("country_id"),
                                ind,
                                cityObject.getString("city_name"),
                                cityObject.getString("city_image").replace("\\","")));
                    }

                    for(CityModel city : cityList) {
                        citiesList.add(new HorizontalRecyclerViewItem(city.getCity_image(), city.getCity_name(), ""));
                    }
                    Log.d("CHANGED SET", "Notify dataset changed city");
                    cityAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void setSummerAndWinter() {
        // Php file which returns the Summer and Winter places data
        final String URL_PLACES = "https://visitcountryapi.000webhostapp.com/exploreSummerWinter.php";

        // Sending the data request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PLACES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d("RESPONSE", "Response SUMMER WINTER success " + response);

                    // Randomly selecting data to display
                    Vector<Integer> summerIndexVector = new Vector<Integer>();
                    Vector<Integer> winterIndexVector = new Vector<>();
                    int halfLenght = array.length()/2;
                    summerIndexVector = randomNumGen(0 , halfLenght, NUM_OF_RECYCLER_ITEMS);
                    winterIndexVector = randomNumGen(halfLenght+1, array.length(), NUM_OF_RECYCLER_ITEMS);

                    Log.d("SUMMER", "Summer index" + summerIndexVector);
                    Log.d("WINTER", "Winter index" + winterIndexVector);

                    for(int ind : summerIndexVector) {
                        JSONObject summerObject = array.getJSONObject(ind);
                        summerList.add(new SummerAndWinterModel(ind,
                                summerObject.getInt("country_id"),
                                summerObject.getString("place_name"),
                                summerObject.getString("geo_thermal_zone"),
                                summerObject.getString("place_image").replace("\\", ""),
                                summerObject.getString("intended_temp")));
                    }

                    for(int ind : winterIndexVector) {
                        JSONObject winterObject = array.getJSONObject(ind);
                        winterList.add(new SummerAndWinterModel(ind,
                                winterObject.getInt("country_id"),
                                winterObject.getString("place_name"),
                                winterObject.getString("geo_thermal_zone"),
                                winterObject.getString("place_image").replace("\\", ""),
                                winterObject.getString("intended_temp")));
                    }

                    for(SummerAndWinterModel summerObj : summerList) {
                        summerItemList.add(new HorizontalRecyclerViewItem(summerObj.getPlace_image(), summerObj.getPlace_name(), ""));
                    }

                    for(SummerAndWinterModel winterObj : winterList) {
                        winterItemList.add(new HorizontalRecyclerViewItem(winterObj.getPlace_image(), winterObj.getPlace_name(), ""));
                    }

                    Log.d("CHANGED SET", "Notify dataset changed summer and winter");
                    summerAdapter.notifyDataSetChanged();
                    winterAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    // Country RecyclerView OnClick method
    @Override
    public void onItemClick(int position) {
        Fragment countryInfoFrag = new CountryInfoFragment(countryList.get(countriesList.get(position).getId() - 1), countryList);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, countryInfoFrag).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onLongItemClick(int position) {
    }

    // Method generating random indexes, to display data
    public Vector<Integer> randomNumGen(int origin, int limit, int numOfItems)
    {
        Vector<Integer> IndexVector = new Vector<Integer>();
        Random rand = new Random();

        for(int i = 0; i < numOfItems; i++) {
            if(IndexVector.isEmpty()) {
                IndexVector.add(ThreadLocalRandom.current().nextInt(origin,limit ));
            } else {
                int index = ThreadLocalRandom.current().nextInt(origin, limit);
                while(IndexVector.contains(index)) {
                    index = ThreadLocalRandom.current().nextInt(origin, limit);
                }
                IndexVector.add(index);
            }
        }
        return IndexVector;
    }
}