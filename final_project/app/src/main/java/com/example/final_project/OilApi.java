package com.example.final_project;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface OilApi {
    @Headers("Accept: application/json")
    @GET("/api/uws/v1/inventory?page=1&perPage=100&serviceKey=LdnwhmjxQqjPZDKQOPuRz6Iof3Z816TsOBUbxKg7ICboASaYuXKJndIuUcR%2BMYsDXzr5NLzGJaBibdX1MkOyLw%3D%3D") //다시 봐야 함
    Call<inventory_api> getStoresByGeo(@Query("lat") double lat, @Query("lng")double lng);
}
