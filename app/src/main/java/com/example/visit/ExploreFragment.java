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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.visit.recyclerView.CountryRecyclerViewAdapter;
import com.example.visit.recyclerView.CountryRecyclerViewItem;
import com.example.visit.recyclerView.HorizontalRecyclerViewAdapter;
import com.example.visit.recyclerView.HorizontalRecyclerViewItem;
import com.example.visit.recyclerView.RecyclerViewClickInterface;
import com.example.visit.recyclerView.VerticalRecyclerViewAdapter;
import com.example.visit.recyclerView.VerticalRecyclerViewItem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExploreFragment extends Fragment implements RecyclerViewClickInterface {
    private RecyclerView cityView;
    private RecyclerView summerAndWinterView;
    private RecyclerView countryView;

    private static final int NUM_OF_COUNTRIES = 5;

    // Adapter is a sort of a bridge between our data (verticalItems) and the RV
    // Adapter always provides as many items as we need at the time which means optimal performance
    private RecyclerView.Adapter cityAdapter;
    private RecyclerView.Adapter summerAndWinterAdapter;
    private RecyclerView.Adapter countryAdapter;


    // Responsible for placing items into our list
    private RecyclerView.LayoutManager cityLayoutManager;
    private RecyclerView.LayoutManager summerAndWinterLayoutManager;
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


    ArrayList<ArticleModel> articleList;
    ArrayList<CountryModel> countryList;
    ArrayList<CityModel> cityList;

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
        summerItemList = new ArrayList<>();
        winterItemList = new ArrayList<>();

        setArticles();
        setCountries();
        setCities();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);



        // TODO implementation of real data
        // Horizontal items should be placed to ArrayList<ArrayList<HorizontalRecyclerViewItem>>
        // Vertical items should be placed to ArrayList<VerticalRecyclerViewItem>
        // All elements of ArrayList<ArrayList<HorizontalRecyclerViewItem>> should be members of ArrayList<VerticalRecyclerViewItem>

        // vertical is used to represent horizontal recycler view items

        cityElement = view.findViewById(R.id.explore_cities_item);
        cityCategoryTitle = cityElement.findViewById(R.id.verticalRecyclerViewItemTitle);
        cityCategoryText = cityElement.findViewById(R.id.verticalRecyclerViewItemText);
        cityCategoryTitle.setText(R.string.cityCategoryTitle);
        cityCategoryText.setText(R.string.cityCategorySubtitle);


        ArrayList<VerticalRecyclerViewItem> summerAndWinterCategory = new ArrayList<>();
        summerAndWinterCategory.add(new VerticalRecyclerViewItem("Smell the sea and feel the sky", "Best summer vacation spots", summerItemList));
        summerAndWinterCategory.add(new VerticalRecyclerViewItem("The joys of winter", "Text", winterItemList));

        countryElement = view.findViewById(R.id.explore_countries_item);
        countryCategoryTitle = countryElement.findViewById(R.id.verticalRecyclerViewItemTitle);
        countryCategoryText = countryElement.findViewById(R.id.verticalRecyclerViewItemText);
        countryCategoryTitle.setText(R.string.countryCategoryTitle);
        countryCategoryText.setText(R.string.countryCategorySubtitle);

        cityView = cityElement.findViewById(R.id.horizontalRecyclerView);
        countryView = countryElement.findViewById(R.id.horizontalRecyclerView);
        summerAndWinterView = view.findViewById(R.id.explore_summer_and_winter_recycler);

        // Needs to be set to true if recycler view won't change in size for performance gains
        cityView.setHasFixedSize(true);
        countryView.setHasFixedSize(true);
        summerAndWinterView.setHasFixedSize(true);

        cityLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cityAdapter = new HorizontalRecyclerViewAdapter(citiesList, getContext());
        cityView.setLayoutManager(cityLayoutManager);
        cityView.setAdapter(cityAdapter);

        countryLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        countryAdapter = new CountryRecyclerViewAdapter(countriesList, getContext(), this);
        countryView.setLayoutManager(countryLayoutManager);
        countryView.setAdapter(countryAdapter);

        summerAndWinterLayoutManager = new LinearLayoutManager(getContext());
        summerAndWinterAdapter = new VerticalRecyclerViewAdapter(getContext(), summerAndWinterCategory);
        summerAndWinterView.setLayoutManager(summerAndWinterLayoutManager);
        summerAndWinterView.setAdapter(summerAndWinterAdapter);


        //Filling articles with data
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

    private void setCountries() {
        final String URL_COUNTRIES = "https://visitcountryapi.000webhostapp.com/exploreCountries.php";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_COUNTRIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d("RESPONSE", "Response COUNTRY success " + response);


                    Vector<Integer> countryIndexVector = new Vector<Integer>();
                    Random rand = new Random();

                    for(int i = 0; i < NUM_OF_COUNTRIES; i++) {
                        if(countryIndexVector.isEmpty()) {
                            countryIndexVector.add(rand.nextInt(59));
                        } else {
                            int countryIndex = rand.nextInt(59);
                            while(countryIndexVector.contains(countryIndex)) {
                                countryIndex = rand.nextInt();
                            }
                            countryIndexVector.add(countryIndex);
                        }
                    }


                    Log.d("COUNTRY", "Country index" + countryIndexVector);

                    for(int ind : countryIndexVector) {
                        JSONObject countryObject = array.getJSONObject(ind);
                        countryList.add(new CountryModel(ind,
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
                                countryObject.getString("call_code")));
                    }

                    for(CountryModel country : countryList) {
                        countriesList.add(new CountryRecyclerViewItem(country.getCountry_image(), country.getCountry_flag(), country.getCountry_name(), ""));
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

    private void setArticles() {
        final String URL_ARTICLES = "https://visitcountryapi.000webhostapp.com/exploreArticles.php";

        Log.d("RESPONSE ARTICLES", "Request started");


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ARTICLES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d("RESPONSE", "Response ARTICLE success " + response);


                    int articleIndex, prevIndex;
                    Random rand = new Random();
                    articleIndex = rand.nextInt(10);

                    Log.d("RESPONSE", "Article index" + articleIndex);

                    JSONObject articleObject = array.getJSONObject(articleIndex);

                    articleList.add(new ArticleModel(articleIndex,
                            articleObject.getString("title"),
                            articleObject.getString("subtitle"),
                            articleObject.getString("link").replace("\\", ""),
                            articleObject.getString("image_url").replace("\\", ""),
                            articleObject.getString("type")));


                    prevIndex = articleIndex;
                    while (prevIndex == articleIndex) {
                        articleIndex = rand.nextInt(10);
                    }
                    Log.d("RESPONSE", String.valueOf(articleIndex));

                    articleObject = array.getJSONObject(articleIndex);

                    articleList.add(new ArticleModel(articleIndex,
                            articleObject.getString("title"),
                            articleObject.getString("subtitle"),
                            articleObject.getString("link").replace("\\", ""),
                            articleObject.getString("image_url").replace("\\", ""),
                            articleObject.getString("type")));

                    Log.d("RESPONSE ARTICLE", articleObject.getString("title"));

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
        final String URL_COUNTRIES = "https://visitcountryapi.000webhostapp.com/exploreCities.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_COUNTRIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d("RESPONSE", "Response CITY success " + response);


                    Vector<Integer> cityIndexVector = new Vector<Integer>();
                    Random rand = new Random();

                    for(int i = 0; i < NUM_OF_COUNTRIES; i++) {
                        if(cityIndexVector.isEmpty()) {
                            cityIndexVector.add(rand.nextInt(196));
                        } else {
                            int cityIndex = rand.nextInt(196);
                            while(cityIndexVector.contains(cityIndex)) {
                                cityIndex = rand.nextInt();
                            }
                            cityIndexVector.add(cityIndex);
                        }
                    }


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


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Country clicked " + countriesList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongItemClick(int position) {
    }
}