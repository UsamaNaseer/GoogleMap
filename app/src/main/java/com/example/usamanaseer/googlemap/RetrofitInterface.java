package com.example.usamanaseer.googlemap;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by UsamaNaseer on 10/16/2017.
 */

public interface RetrofitInterface {
    @GET("maps/api/place/nearbysearch/json")
    Call<PlacesModel> placeslist(@QueryMap Map<String,String> options);

}
