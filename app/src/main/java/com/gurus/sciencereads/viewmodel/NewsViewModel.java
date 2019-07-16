package com.gurus.sciencereads.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class NewsViewModel extends ViewModel {

    private NewsItemModel newsItemModel;
    private final BehaviorSubject<String> selectedNews = BehaviorSubject.create();

    public NewsViewModel(){
        newsItemModel = new NewsItemModel();
    }

    public Observable<String> getNewsUrl(){
        return newsItemModel.getUrl();
    }

}
