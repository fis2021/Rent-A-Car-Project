package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.LinkedList;

public class CarView extends Car
{
    private ImageView image;

    public CarView(int id, String marca, int kilometri, String oras, double rating, int numberOfRates, String imagePath, LinkedList<String> usersWhoGaveFeedback)
    {
        super(id, marca, kilometri, oras, rating, numberOfRates, imagePath, usersWhoGaveFeedback);
        image = new ImageView(new Image(getImagePath()));
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void addUserWhoGaveFeedback(String userName)
    {
        if (usersWhoGaveFeedback == null)
        {
            usersWhoGaveFeedback = new LinkedList<String>();
        }

        usersWhoGaveFeedback.add(userName);
    }
}
