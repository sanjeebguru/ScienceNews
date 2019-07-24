package com.gurus.sciencereads.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.Nullable;

import com.gurus.sciencereads.databinding.ActivityMainBinding;
import com.gurus.sciencereads.retrofit.Controller;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class NewsViewModel extends ViewModel implements LifecycleObserver {

    private MutableLiveData<List<Article>> newsDataList;

    public LiveData<List<Article>> getNewsData(){
        return newsDataList;
    }
    Controller controller = new Controller();
    public void init(Context context){

//        if (newsDataList!=null){
//            return;
//        }
        //controller.start();
        newsDataList = controller.getaNewsList();
     }



//    private NewsItemModel newsItemModel;
//    private final BehaviorSubject<String> selectedNews = BehaviorSubject.create();
//
//    public NewsViewModel() {
//        newsItemModel = new NewsItemModel();
//    }
//
//    public Observable<String> getNewsUrl() {
//        return newsItemModel.getUrl();
//    }

}
