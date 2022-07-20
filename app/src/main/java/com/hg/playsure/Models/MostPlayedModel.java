package com.hg.playsure.Models;

public class MostPlayedModel {

    private String name;
    private String imgURL;
    private String gameURL;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL(){
        return imgURL;
    }
    public void setImgURL(String imgURL){
        this.imgURL = imgURL;
    }

    public String getGameURL() { return gameURL; }
    public void setGameURL(String gameURL) { this.gameURL = gameURL; }

}