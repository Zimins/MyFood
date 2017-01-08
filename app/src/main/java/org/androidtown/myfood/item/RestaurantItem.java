package org.androidtown.myfood.item;

import java.io.Serializable;

/**
 * Created by Zimincom on 2016. 11. 30..
 */

public class RestaurantItem implements Serializable{

    public int id;
    public String name;
    public float rating;
    public String imgSrc;
    public String contact;
    public double user_distance_meter;
    public String summary;
    public int isSmoke;
    public int isParking;

    public float latitude;
    public float longitude;

    public RestaurantItem(int id,String name,String imgSrc,float latitude,float longitude,String summary,int isSmoke,int isParking,float rating){
        this.id = id;
        this.name = name;
        this.imgSrc = imgSrc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.summary = summary;
        this.isSmoke = isSmoke;
        this.isParking = isParking;
        this.rating = rating;

    }

    @Override
    public String toString() {
        return "RestaurantItem{" +
                "id=" + id +
                ", name='" + name + '\'' +

                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public float getLatitude() {
        return latitude;
    }

    public double getUser_distance_meter() {
        return user_distance_meter;
    }

    public float getLongitude() {
        return longitude;
    }
}
