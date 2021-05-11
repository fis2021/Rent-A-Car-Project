package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CarView extends Car
{
    private ImageView image;

    public CarView(int id, String marca, int kilometri, String oras, int rating, String imagePath)
    {
        super(id, marca, kilometri, oras, rating, imagePath);
        image = new ImageView(new Image(getImagePath()));
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
