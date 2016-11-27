package com.king.king_lens.Grid_List;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class CollectionProduct {
    private int imageId;
    private String title;
    private String imageUrl;
    private String id;

    public CollectionProduct(String imageUrl,String id, String title) {
        this.imageId = imageId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.id = id;

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

    public String getId()
    {
        return id;
    }


}
