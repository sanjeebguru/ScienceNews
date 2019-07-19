package com.gurus.sciencereads.retrofit;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gurus.sciencereads.config.Config;
import com.gurus.sciencereads.viewmodel.Article;
import com.gurus.sciencereads.viewmodel.NewsItemModel;
import com.gurus.sciencereads.viewmodel.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Controller implements Callback<ResponseModel> {

    private static final String BASE_URL = "https://newsapi.org/v2/";
    private List<Article> dataSet = new ArrayList<>();

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NewsAPI newsAPI = retrofit.create(NewsAPI.class);

        Call<ResponseModel> call = newsAPI.getLatestNews("new-Scientist", Config.aoiKey);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
        if(response.isSuccessful()) {
            ResponseModel changesList = response.body();
            dataSet = changesList.getArticles();
            Log.d("-----news",changesList.getStatus());
        } else {
            System.out.println("----news----"+response.message());
        }
    }

    @Override
    public void onFailure(Call<ResponseModel> call, Throwable t) {
        t.printStackTrace();
    }

    public MutableLiveData<List<Article>> getaNewsList(){
        start();
        MutableLiveData<List<Article>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        //Log.d("-----dataset",dataSet.get(0).getTitle());
        return data;
    }
}
