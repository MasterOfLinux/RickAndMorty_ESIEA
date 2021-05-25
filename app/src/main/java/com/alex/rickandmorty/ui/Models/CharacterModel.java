package com.alex.rickandmorty.ui.Models;

public class CharacterModel {
    String name,status,specie,image,id,curUrl,i;


    public CharacterModel(String name, String status, String specie, String image, String id, String curUrl,String i) {
        this.name = name;
        this.status = status;
        this.specie = specie;
        this.image = image;
        this.id = id;
        this.curUrl = curUrl;
        this.i = i;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurUrl() {
        return curUrl;
    }

    public void setCurUrl(String curUrl) {
        this.curUrl = curUrl;
    }
}
