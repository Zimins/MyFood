package org.androidtown.myfood.item;

/**
 * Created by Zimincom on 2016. 11. 8..
 */

public class ListItem {

    private int id;
    private String text;
    private int imgRes;

    public ListItem(String text, int imgRes) {
        this.text = text;
        this.imgRes = imgRes;
    }

    public int getId(){
        return this.id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return imgRes;
    }

    public void setImage(String image) {
        this.imgRes = imgRes;
    }
}
