package com.king.king_lens.Grid_List;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class CollectionProduct {
    private int imageId;
    private String title;
    private String imageUrl;

    public CollectionProduct(String imageUrl, String title) {
        this.imageId = imageId;
        this.title = title;
        this.imageUrl = imageUrl;

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

    public String getImageUrl()
    {
        return imageUrl;
    }


}
