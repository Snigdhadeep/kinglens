package com.king.king_lens.Home_Sliding;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Rp on 3/30/2016.
 */


//********GRIDVIEW************
public class Beanclass implements Serializable{
    private int image1;

    private Bitmap bmp;

    private Integer imgaeId;


    public Beanclass(int image) {
        this.image1 = image;
       /* this.title1 = title;
        this.discription1 = discription;
        this.date1 = date;*/
    }

    public void setBmp(Bitmap bmp)
    {
        this.bmp = bmp;
    }

    public void setImageId(Integer id)
    {
        this.imgaeId = id;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public Integer getImgaeId()
    {
        return imgaeId;
    }



    public int getImage1() {
        return image1;
    }

  /*  public void setImage1(int image1) {
        this.image1 = image1;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getDiscription1() {
        return discription1;
    }

    public void setDiscription1(String discription1) {
        this.discription1 = discription1;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }*/
}
