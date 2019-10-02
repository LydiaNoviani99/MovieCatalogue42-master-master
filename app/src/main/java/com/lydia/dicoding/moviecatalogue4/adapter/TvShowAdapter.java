package com.lydia.dicoding.moviecatalogue4.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lydia.dicoding.moviecatalogue4.R;
import com.lydia.dicoding.moviecatalogue4.entity.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {
    private ArrayList<TvShow> tvShows;
    private TvShowDataListener tvShowDataListener;

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tvshow_item, viewGroup, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int i) {
        final TvShow tvShow = tvShows.get(i);
        holder.tvTitle.setText(tvShow.getTitle());
        holder.tvOverview.setText(tvShow.getOverview());
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w185" + tvShow.getPosterPath())
                .into(holder.ivPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvShowDataListener.onTvShowItemClicked(tvShow);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tvShows == null)
            return 0;
        return tvShows.size();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvOverview;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
        }
    }

    public interface TvShowDataListener {
        void onTvShowItemClicked(TvShow tvShow);
    }

    public ArrayList<TvShow> getTvShows() {
        if (tvShows == null) {
            tvShows = new ArrayList<>();
        }
        return tvShows;
    }

    public void setTvShows(ArrayList<TvShow> tvShows) {
        this.getTvShows().clear();
        this.getTvShows().addAll(tvShows);
        notifyDataSetChanged();
    }

    public TvShowDataListener getTcShowDataListener() {
        return tvShowDataListener;
    }

    public void setTvShowDataListener(TvShowDataListener tvShowDataListener) {
        this.tvShowDataListener = tvShowDataListener;
    }
}
