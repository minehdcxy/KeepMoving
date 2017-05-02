package com.example.raytine.keepmoving.home.model;

import java.io.Serializable;

/**
 * Created by raytine on 2017/4/30.
 */

public class FilmData implements Serializable{
    private String filmName;
    private String filmIntroduction;
    private String filmPrice;
    private String filmImage;
    private String filmId;
    private String filmType;

    private String filmVersion;
    private String filmAddress;
    private String filmTime;
    private String filmDirector;

    public String getFilmStr() {
        return filmStr;
    }

    public void setFilmStr(String filmStr) {
        this.filmStr = filmStr;
    }

    private String filmStr;

    public String getFilmVersion() {
        return filmVersion;
    }

    public void setFilmVersion(String filmVersion) {
        this.filmVersion = filmVersion;
    }

    public String getFilmDirector() {
        return filmDirector;
    }

    public void setFilmDirector(String filmDirector) {
        this.filmDirector = filmDirector;
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

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getFilmPrice() {
        return filmPrice;
    }

    public void setFilmPrice(String filmPrice) {
        this.filmPrice = filmPrice;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmIntroduction() {
        return filmIntroduction;
    }

    public void setFilmIntroduction(String filmIntroduction) {
        this.filmIntroduction = filmIntroduction;
    }

    public String getFilmImage() {
        return filmImage;
    }

    public void setFilmImage(String filmImage) {
        this.filmImage = filmImage;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("filmName : ").append(filmName)
                .append(", filmIntroduction : ").append(filmIntroduction)
                .append(", filmPrice : ").append(filmPrice)
                .append(", filmImage : ").append(filmImage);
        return sb.toString();
    }
}
