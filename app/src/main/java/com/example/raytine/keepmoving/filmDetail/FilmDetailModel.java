package com.example.raytine.keepmoving.filmDetail;

/**
 * Created by raytine on 2017/5/1.
 */

public class FilmDetailModel {
    private String filmName;
    private String filmType;
    private String filmVersion;
    private String filmAddress;
    private String filmTime;
    private String director;
    private String cinemaStr;

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public String getFilmVersion() {
        return filmVersion;
    }

    public void setFilmVersion(String filmVersion) {
        this.filmVersion = filmVersion;
    }

    public String getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(String filmTime) {
        this.filmTime = filmTime;
    }

    public String getFilmAddress() {
        return filmAddress;
    }

    public void setFilmAddress(String filmAddress) {
        this.filmAddress = filmAddress;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCinemaStr() {
        return cinemaStr;
    }

    public void setCinemaStr(String cinemaStr) {
        this.cinemaStr = cinemaStr;
    }
}
