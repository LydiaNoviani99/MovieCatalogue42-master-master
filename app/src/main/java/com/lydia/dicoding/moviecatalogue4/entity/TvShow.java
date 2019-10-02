package com.lydia.dicoding.moviecatalogue4.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShow implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String posterPath;

    public TvShow() {
    }

    public TvShow(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("name");
            String overview = object.getString("overview");
            String posterPath = object.getString("poster_path");

            this.id = id;
            this.title = title;
            this.overview = overview;
            this.posterPath = posterPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
    }

    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}