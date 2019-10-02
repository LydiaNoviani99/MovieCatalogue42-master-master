package com.lydia.dicoding.moviecatalogue4.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lydia.dicoding.moviecatalogue4.DetailActivity;
import com.lydia.dicoding.moviecatalogue4.R;
import com.lydia.dicoding.moviecatalogue4.adapter.TvShowAdapter;
import com.lydia.dicoding.moviecatalogue4.entity.TvShow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.TvshowColumns.CONTENT_URI;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.TvshowColumns.OVERVIEW;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.TvshowColumns.POSTER_PATH;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.TvshowColumns.TITLE;

public class FavouriteTvFragment extends Fragment implements TvShowAdapter.TvShowDataListener {

    @BindView(R.id.rv_tv)
    RecyclerView rvTv;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private TvShowAdapter tvShowAdapter;

    public FavouriteTvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        tvShowAdapter = new TvShowAdapter();
        tvShowAdapter.setTvShowDataListener(this);
        rvTv.setAdapter(tvShowAdapter);
        Toast.makeText(getContext(), "aaa", Toast.LENGTH_SHORT).show();
        new LoadNoteAsync().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadNoteAsync().execute();
    }

    @Override
    public void onTvShowItemClicked(TvShow tvShow) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TVSHOW, tvShow);
        startActivity(intent);
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        protected void onPostExecute(Cursor tv_shows) {
            super.onPostExecute(tv_shows);
            progressBar.setVisibility(View.GONE);

            ArrayList<TvShow> list = populateTvShow(tv_shows);
            tvShowAdapter.setTvShows(list);
            tvShowAdapter.notifyDataSetChanged();

            if (list.size() == 0) {
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvTv, message, Snackbar.LENGTH_SHORT).show();
    }

    private ArrayList<TvShow> populateTvShow(Cursor cursor) {
        ArrayList<TvShow> arrayList = new ArrayList();
        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                tvShow.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                arrayList.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
}