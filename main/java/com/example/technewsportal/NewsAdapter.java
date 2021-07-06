package com.example.technewsportal;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<NewsItem> myNewsList;
    private OnItemClickListener myListener;

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        public ImageView newsPic;
        public TextView newsName;

        public NewsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            //Finds views
            newsPic = itemView.findViewById(R.id.newsPic);
            newsName = itemView.findViewById(R.id.newsHeader);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        //Finds listener and uses this interface
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        myListener = listener;
    }

    public NewsAdapter(ArrayList<NewsItem> newsList){
        myNewsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Sets view of adapter
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        NewsViewHolder ovh = new NewsViewHolder(v, myListener);
        return ovh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem currentNews = myNewsList.get(position);
        holder.newsName.setText(currentNews.getTitle());
        holder.newsPic.setImageDrawable(currentNews.getPhoto());
    }

    @Override
    public int getItemCount() {
        return myNewsList.size();
    }
}
