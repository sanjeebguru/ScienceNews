package com.gurus.sciencereads;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.gurus.sciencereads.adapter.NewsRecyclerViewAdapter;
import com.gurus.sciencereads.databinding.ActivityMainBinding;
import com.gurus.sciencereads.retrofit.Controller;
import com.gurus.sciencereads.viewmodel.Article;
import com.gurus.sciencereads.viewmodel.NewsItemModel;
import com.gurus.sciencereads.viewmodel.NewsViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity-----";
    private ActivityMainBinding activityMainBinding;
    private NewsViewModel newsViewModel;
    private NewsRecyclerViewAdapter adapter;
    ItemTouchHelper.SimpleCallback simpleCallback;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Article> newsItemModels;

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
        initSwipeDelete();
        newsViewModel.getNewsData().observe(this, new Observer<List<Article>>() {


            @Override
            public void onChanged(@Nullable final List<Article> newsItemModels) {
                MainActivity.this.newsItemModels = newsItemModels;
                Log.d(TAG, "changed"+newsItemModels.size());
                adapter = new NewsRecyclerViewAdapter(MainActivity.this, newsItemModels);
                activityMainBinding.newsList.setAdapter(adapter);
                mSwipeRefreshLayout.setRefreshing(false);
                simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        if (newsItemModels.get(viewHolder.getAdapterPosition()).isFavourite()){
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        if (newsItemModels.get(viewHolder.getAdapterPosition()).isFavourite()){
                            return;
                        }
                        newsItemModels.remove(viewHolder.getAdapterPosition());
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(activityMainBinding.newsList);
            }
        });
       // Controller controller = new Controller();
       // controller.start();
    }

    private void initSwipeDelete() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                newsViewModel.init(MainActivity.this);
                newsViewModel.getNewsData().observe(MainActivity.this, new Observer<List<Article>>() {
                    @Override
                    public void onChanged(@Nullable List<Article> articles) {
                        adapter.updateData(articles);
                        //Log.d(TAG+"--swiped refreshed",articles.get(1).getTitle());
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
//        mSwipeRefreshLayout.post(new Runnable() {
//
//            @Override
//            public void run() {
//
//                mSwipeRefreshLayout.setRefreshing(true);
//
//                // Fetching data from server
//                newsViewModel.init(MainActivity.this);
//            }
//        });


        io.reactivex.Observer<List<Article>> observer = new io.reactivex.Observer<List<Article>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Article> articles) {
                newsItemModels =  articles;
                adapter.updateData(newsItemModels);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
