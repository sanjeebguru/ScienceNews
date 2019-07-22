package com.gurus.sciencereads.adapter;

import android.content.Context;
import android.graphics.ColorFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gurus.sciencereads.R;
import com.gurus.sciencereads.databinding.NewsBinding;
import com.gurus.sciencereads.viewmodel.Article;
import com.gurus.sciencereads.viewmodel.NewsItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private List<Article> newsItemModelList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    boolean lottieFav = false;

    public NewsRecyclerViewAdapter(Context context, List<Article> newsItemModels) {
        this.newsItemModelList = newsItemModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       // View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item,viewGroup,false);
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        NewsBinding binding = NewsBinding.inflate(layoutInflater, viewGroup, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.newsBinding.textView.setText(newsItemModelList.get(i).getDescription());
        Picasso.get().load(newsItemModelList.get(i).getUrlToImage()).into(viewHolder.newsBinding.imageView);

    }

    @Override
    public int getItemCount() {
        return newsItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private NewsBinding newsBinding;

        public ViewHolder(final NewsBinding binding) {
            super(binding.getRoot());
            this.newsBinding = binding;
            init();
        }

        private void init() {
            newsBinding.lottie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!lottieFav) {
                        newsBinding.lottie.playAnimation();
                        lottieFav = true;
                    }
                    else {
                        newsBinding.lottie.setProgress(0.0f);
                        lottieFav = false;
                    }
                }
            });
        }
    }
}
