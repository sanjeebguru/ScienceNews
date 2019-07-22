package com.gurus.sciencereads;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.gurus.sciencereads.adapter.NewsRecyclerViewAdapter;
import com.gurus.sciencereads.databinding.ActivityMainBinding;
import com.gurus.sciencereads.retrofit.Controller;
import com.gurus.sciencereads.viewmodel.Article;
import com.gurus.sciencereads.viewmodel.NewsItemModel;
import com.gurus.sciencereads.viewmodel.NewsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity-----";
    private ActivityMainBinding activityMainBinding;
    private NewsViewModel newsViewModel;
    private NewsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //adapter = new NewsRecyclerViewAdapter(this, newsViewModel.getNewsData().ob);
        activityMainBinding.newsList.setLayoutManager(new LinearLayoutManager(this));
        //activityMainBinding.newsList.setAdapter(adapter);
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.init(this);
        newsViewModel.getNewsData().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> newsItemModels) {
                Log.d(TAG, "changed"+newsItemModels.size());
                adapter = new NewsRecyclerViewAdapter(MainActivity.this, newsItemModels);
                activityMainBinding.newsList.setAdapter(adapter);
            }
        });
       // Controller controller = new Controller();
       // controller.start();
    }
}
