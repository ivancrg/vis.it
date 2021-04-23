package com.example.visit;

import retrofit2.Call;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface CountryCityAPI {
    @GET
    Call<List<CountryPost>> getPosts();

    @POST
    Call<CountryPost> createPost(@Body CountryPost post);
}
