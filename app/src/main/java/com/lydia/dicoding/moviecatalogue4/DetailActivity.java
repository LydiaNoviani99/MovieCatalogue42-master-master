package com.lydia.dicoding.moviecatalogue4;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lydia.dicoding.moviecatalogue4.db.DatabaseContract;
import com.lydia.dicoding.moviecatalogue4.entity.Movie;
import com.lydia.dicoding.moviecatalogue4.entity.TvShow;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.BaseColumns._ID;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.MovieColumns.POPULARITY;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.lydia.dicoding.moviecatalogue4.db.DatabaseContract.MovieColumns.TITLE;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private Movie movie;
    public static final String EXTRA_TVSHOW = "extra_tvshow";
    private TvShow tvshow;

    @BindView(R.id.fab_favorite)
    FloatingActionButton fabFavorite;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_popularity)
    TextView tvPopularity;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            tvReleaseDate.setText(movie.getReleaseDate());
            tvPopularity.setText(getString(R.string.popularity, Integer.toString(movie.getPopularity())));
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w342" + movie.getPosterPath())
                    .into(ivPoster);
        }
        else{
            tvshow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
            tvTitle.setText(tvshow.getTitle());
            tvOverview.setText(tvshow.getOverview());
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w342" + tvshow.getPosterPath())
                    .into(ivPoster);
        }

    }

    @OnClick(R.id.fab_favorite)
    public void saveToFavorite() {

        if(movie!=null){
            Cursor cursor = getContentResolver().query(Uri.parse(DatabaseContract.MovieColumns.CONTENT_URI + "/" + movie.getId()), null, null, null, null);
            if (cursor.getCount() == 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(_ID, movie.getId());
                contentValues.put(TITLE, movie.getTitle());
                contentValues.put(OVERVIEW, movie.getOverview());
                contentValues.put(POSTER_PATH, movie.getPosterPath());
                contentValues.put(RELEASE_DATE, movie.getReleaseDate());
                contentValues.put(POPULARITY, movie.getPopularity());
                getContentResolver().insert(DatabaseContract.MovieColumns.CONTENT_URI, contentValues);
                Toast.makeText(DetailActivity.this, movie.getTitle() + " save to favorite movies", Toast.LENGTH_LONG).show();
            } else {
                long deleted = getContentResolver().delete(Uri.parse(DatabaseContract.MovieColumns.CONTENT_URI + "/" + movie.getId()), null, null);
                if (deleted > 0) {
                    getContentResolver().notifyChange(DatabaseContract.MovieColumns.CONTENT_URI, null);
                }
                Toast.makeText(DetailActivity.this, movie.getTitle() + " remove from favorite movies", Toast.LENGTH_LONG).show();
            }
        }

        if(tvshow != null){
            Cursor cursor1 = getContentResolver().query(Uri.parse(DatabaseContract.TvshowColumns.CONTENT_URI_TV + "/" + tvshow.getId()), null, null, null, null);
            if (cursor1.getCount() == 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(_ID, tvshow.getId());
                contentValues.put(TITLE, tvshow.getTitle());
                contentValues.put(OVERVIEW, tvshow.getOverview());
                contentValues.put(POSTER_PATH, tvshow.getPosterPath());
                getContentResolver().insert(DatabaseContract.TvshowColumns.CONTENT_URI_TV, contentValues);
                Toast.makeText(DetailActivity.this, tvshow.getTitle() + " save to favorite tv shows", Toast.LENGTH_LONG).show();
            } else {
                long deleted = getContentResolver().delete(Uri.parse(DatabaseContract.TvshowColumns.CONTENT_URI_TV + "/" + tvshow.getId()), null, null);
                if (deleted > 0) {
                    getContentResolver().notifyChange(DatabaseContract.TvshowColumns.CONTENT_URI_TV, null);
                }
                Toast.makeText(DetailActivity.this, tvshow.getTitle() + " remove from favorite tv shows", Toast.LENGTH_LONG).show();
            }
        }




    }
}

