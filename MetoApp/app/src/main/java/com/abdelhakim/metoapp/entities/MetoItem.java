package com.abdelhakim.metoapp.entities;

import java.util.Date;

public class MetoItem {

    private int temperature;
    private int tempMax;
    private int tempMin;
    private int pression;
    private String image;
    private Date date;
    private Coord coord;

    public MetoItem(int temperature, int tempMax, int tempMin, int pression, String image, Date date, Coord coord) {
        this.temperature = temperature;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.pression = pression;
        this.image = image;
        this.date = date;
        this.coord = coord;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getPression() {
        return pression;
    }

    public void setPression(int pression) {
        this.pression = pression;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
