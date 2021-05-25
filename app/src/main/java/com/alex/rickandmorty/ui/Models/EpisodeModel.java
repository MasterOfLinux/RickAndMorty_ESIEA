package com.alex.rickandmorty.ui.Models;

public class EpisodeModel {

    String name, airDate, episdoe;

    public EpisodeModel(String name, String airDate, String episdoe) {
        this.name = name;
        this.airDate = airDate;
        this.episdoe = episdoe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getEpisdoe() {
        return episdoe;
    }

    public void setEpisdoe(String episdoe) {
        this.episdoe = episdoe;
    }
}
