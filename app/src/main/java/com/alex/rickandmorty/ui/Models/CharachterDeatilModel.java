package com.alex.rickandmorty.ui.Models;

public class CharachterDeatilModel {

    String name,episode;

    public CharachterDeatilModel(String name, String episode) {
        this.name = name;
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}
