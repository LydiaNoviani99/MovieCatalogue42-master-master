package com.lydia.dicoding.moviecatalogue4.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lydia.dicoding.moviecatalogue4.BuildConfig;
import com.lydia.dicoding.moviecatalogue4.DetailActivity;
import com.lydia.dicoding.moviecatalogue4.R;
import com.lydia.dicoding.moviecatalogue4.adapter.TvShowAdapter;
import com.lydia.dicoding.moviecatalogue4.entity.TvShow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
public class TvShowFragment extends Fragment implements TvShowAdapter.TvShowDataListener {

    private static final String STATE_RESULT = "state_result";
    private ArrayList<TvShow> tvShows;

    TvShowAdapter tvShowAdapter;
    ProgressBar progressBar;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvTv = view.findViewById(R.id.rv_tv);
        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        rvTv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        tvShowAdapter = new TvShowAdapter();
        tvShowAdapter.setTvShowDataListener(this);
        rvTv.setAdapter(tvShowAdapter);

        if (savedInstanceState != null) {
            tvShows = savedInstanceState.getParcelableArrayList(STATE_RESULT);
            tvShowAdapter.setTvShows(tvShows);
        }
        else if (tvShows == null) {
            populateTvShows();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_RESULT, tvShows);
    }

    @Override
    public void onTvShowItemClicked(TvShow tvShow) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TVSHOW, tvShow);
        startActivity(intent);
    }

    private void populateTvShows() {
        tvShows = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();

        String url;
        url = "https://api.themoviedb.org/3/tv/popular?api_key=" +
                BuildConfig.TMDB_API_KEY + "&language=en-US";

        progressBar.setVisibility(View.VISIBLE);

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        TvShow tvShow = new TvShow(item);
                        tvShows.add(tvShow);
                    }
                    progressBar.setVisibility(View.GONE);
                    tvShowAdapter.setTvShows(tvShows);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}