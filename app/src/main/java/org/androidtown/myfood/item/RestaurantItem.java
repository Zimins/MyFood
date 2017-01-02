package org.androidtown.myfood.item;

import java.io.Serializable;

/**
 * Created by Zimincom on 2016. 11. 30..
 */

public class RestaurantItem implements Serializable{

    public int id;
    public String name;
    public int raiting;
    public String imgSrc;
    public String contact;

//  public int latitude;
//  public int longtitude;

    public RestaurantItem(int id,String name,String imgSrc){
        this.id = id;
        this.name = name;
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "RestaurantItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", raiting=" + raiting +
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

    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
