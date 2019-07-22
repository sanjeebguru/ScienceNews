package com.gurus.sciencereads;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity-----";
    private ActivityMainBinding activityMainBinding;
    private NewsViewModel newsViewModel;
    private NewsRecyclerViewAdapter adapter;
    ItemTouchHelper.SimpleCallback simpleCallback;

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
            public void onChanged(@Nullable final List<Article> newsItemModels) {
                Log.d(TAG, "changed"+newsItemModels.size());
                adapter = new NewsRecyclerViewAdapter(MainActivity.this, newsItemModels);
                activityMainBinding.newsList.setAdapter(adapter);
                simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
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
}
