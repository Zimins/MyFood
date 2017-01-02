package org.androidtown.myfood.item;

/**
 * Created by Zimincom on 2016. 11. 29..
 */


public class ReviewItem {

    public int restaurant_Id=0;
    public String name="Andy";
    public String text="hello world";

    @Override
    public String toString() {
        return "ReviewItem{" +
                "restaurantId=" + restaurant_Id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
