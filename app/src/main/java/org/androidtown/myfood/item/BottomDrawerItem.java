package org.androidtown.myfood.item;

/**
 * Created by Zimincom on 2016. 11. 2..
 */

public class BottomDrawerItem {
    private String text;
    private int imgRes;
    private int resId;

    public BottomDrawerItem(String text, int imgRes, int resId) {
        this.text = text;
        this.imgRes = imgRes;
        this.resId = resId;
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

    public void setImage(int image) {
        this.imgRes = image; }


    public int getId() {
        return resId;

    }
}
