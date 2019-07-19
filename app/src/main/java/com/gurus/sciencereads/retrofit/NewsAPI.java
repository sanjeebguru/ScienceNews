package com.gurus.sciencereads.retrofit;

import com.gurus.sciencereads.viewmodel.NewsItemModel;
import com.gurus.sciencereads.viewmodel.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("/v2/everything")
    Call<List<NewsItemModel>> loadNews(@Query("q") String status);

    @GET("everything")
    Call<ResponseModel> getLatestNews (@Query("q") String source, @Query("apiKey") String apiKey);
}
