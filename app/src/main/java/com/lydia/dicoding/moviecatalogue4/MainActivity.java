package com.lydia.dicoding.moviecatalogue4;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.lydia.dicoding.moviecatalogue4.fragment.FavouriteTvFragment;
import com.lydia.dicoding.moviecatalogue4.fragment.NowPlayingFragment;
import com.lydia.dicoding.moviecatalogue4.fragment.FavouriteFragment;
import com.lydia.dicoding.moviecatalogue4.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_now_playing:
                    showNowPlaying();
                    return true;
                case R.id.navigation_up_coming:
                    showTvShow();
                    return true;
                case R.id.navigation_favorite:
                    showFavourite();
                    return true;
                case R.id.navigation_favorite_tv:
                    showFavouriteTv();
                    return true;
            }

            return false;
        }
    };

    private void showNowPlaying() {
        getSupportActionBar().setTitle(R.string.now_playing);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        NowPlayingFragment mNowPlayingFragment = new NowPlayingFragment();

        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            mFragmentManager.beginTransaction().remove(fragment).commit();
        }

        mFragmentTransaction.add(R.id.frame_container, mNowPlayingFragment, NowPlayingFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }
    private void showTvShow() {
        getSupportActionBar().setTitle(R.string.tv_show);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        TvShowFragment tvShowFragment = new TvShowFragment();

        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            mFragmentManager.beginTransaction().remove(fragment).commit();
        }

        mFragmentTransaction.add(R.id.frame_container, tvShowFragment, TvShowFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }


    private void showFavourite() {
        getSupportActionBar().setTitle(R.string.favorite);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        FavouriteFragment mFavouriteFragment = new FavouriteFragment();

        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            mFragmentManager.beginTransaction().remove(fragment).commit();
        }

        mFragmentTransaction.add(R.id.frame_container, mFavouriteFragment, FavouriteFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    private void showFavouriteTv() {
        getSupportActionBar().setTitle(R.string.favorite);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        FavouriteTvFragment mFavouriteFragment = new FavouriteTvFragment();

        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            mFragmentManager.beginTransaction().remove(fragment).commit();
        }

        mFragmentTransaction.add(R.id.frame_container, mFavouriteFragment, FavouriteTvFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null)
            showNowPlaying();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_settings:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
