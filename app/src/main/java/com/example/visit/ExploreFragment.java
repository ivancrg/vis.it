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
import com.example.visit.recyclerView.HorizontalRecyclerViewItem;
import com.example.visit.recyclerView.VerticalRecyclerViewAdapter;
import com.example.visit.recyclerView.VerticalRecyclerViewItem;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Random;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExploreFragment extends Fragment {
    private RecyclerView countryAndCityView;
    private RecyclerView summerAndWinterView;

    // Adapter is a sort of a bridge between our data (verticalItems) and the RV
    // Adapter always provides as many items as we need at the time which means optimal performance
    private RecyclerView.Adapter countryAndCityAdapter;
    private RecyclerView.Adapter summerAndWinterAdapter;


    // Responsible for placing items into our list
    private RecyclerView.LayoutManager countryAndCityLayoutManager;
    private RecyclerView.LayoutManager summerAndWinterLayoutManager;


    View travelTipsArticle;
    ImageButton articleButtonTop;
    TextView articleTitleTop;
    TextView articleSubtitleTop;

    View genericArticle;
    ImageButton articleButtonBottom;
    TextView articleTitleBottom;
    TextView articleSubtitleBottom;


    ArrayList<ArticleModel> articleList;


    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        setArticles();


        // TODO implementation of real data
        // Horizontal items should be placed to ArrayList<ArrayList<HorizontalRecyclerViewItem>>
        // Vertical items should be placed to ArrayList<VerticalRecyclerViewItem>
        // All elements of ArrayList<ArrayList<HorizontalRecyclerViewItem>> should be members of ArrayList<VerticalRecyclerViewItem>

        // horizontalItems is used to represent horizontal recycler view items
        ArrayList<HorizontalRecyclerViewItem> horizontalItems = new ArrayList<>();
        horizontalItems.add(new HorizontalRecyclerViewItem(R.drawable.example1, "Title 1", "Text 1"));
        horizontalItems.add(new HorizontalRecyclerViewItem(R.drawable.example2, "Title 2", "Text 2"));
        horizontalItems.add(new HorizontalRecyclerViewItem(R.drawable.example3, "Title 3", "Text 3"));
        horizontalItems.add(new HorizontalRecyclerViewItem(R.drawable.example4, "Title 4", "Text 4"));
        horizontalItems.add(new HorizontalRecyclerViewItem(R.drawable.example5, "Title 5", "Text 5"));

        // vertical is used to represent horizontal recycler view items
        ArrayList<VerticalRecyclerViewItem> countryAndCityCategory = new ArrayList<>();
        countryAndCityCategory.add(new VerticalRecyclerViewItem("Popular countries", "Text", horizontalItems));
        countryAndCityCategory.add(new VerticalRecyclerViewItem("Explore vibrant new places", "Text", horizontalItems));

        ArrayList<VerticalRecyclerViewItem> summerAndWinterCategory = new ArrayList<>();
        summerAndWinterCategory.add(new VerticalRecyclerViewItem("Smell the sea and feel the sky", "Best summer vacation spots", horizontalItems));
        summerAndWinterCategory.add(new VerticalRecyclerViewItem("The joys of winter", "Text", horizontalItems));

        countryAndCityView = view.findViewById(R.id.explore_countries_and_cities_recycler);
        summerAndWinterView = view.findViewById(R.id.explore_summer_and_winter_recycler);

        // Needs to be set to true if recycler view won't change in size for performance gains
        countryAndCityView.setHasFixedSize(true);
        summerAndWinterView.setHasFixedSize(true);

        countryAndCityLayoutManager = new LinearLayoutManager(getContext());
        countryAndCityAdapter = new VerticalRecyclerViewAdapter(getContext(), countryAndCityCategory);

        countryAndCityView.setLayoutManager(countryAndCityLayoutManager);
        countryAndCityView.setAdapter(countryAndCityAdapter);

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

    private void setArticles() {
        final String URL_ARTICLES = "https://thetarkovguide.000webhostapp.com/exploreArticles.php";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ARTICLES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    Log.d("RESPONSE", "Response success " + response);


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

                    Log.d("RESPONSE", articleObject.getString("title"));

                    Log.d("RESPONSE", Boolean.toString(articleList.isEmpty()));

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
}