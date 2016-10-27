package HelperClasses;

/**
 * Created by admin on 3/22/2016.
 */
public class BeanClassForListView {

    private int image;
    private String title;
    private String description;
    private String category;


    public BeanClassForListView(int image, String title, String description, String category) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.category = category;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
