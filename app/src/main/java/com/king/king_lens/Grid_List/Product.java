package com.king.king_lens.Grid_List;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class Product {
    private int imageId;
    private String title;
    private String description;
    private String product_id;
    private String imageUrl;
   // private String price;

    public Product(String product_id, String title, String imageUrl, String description) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.product_id = product_id;
        this.imageUrl = imageUrl;

        //this.price=price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProduct_id()
    {
        return product_id;
    }
    public String getImageUrl()
    {
        return imageUrl;
    }

  //  public String getPrice(){return price;}
}
