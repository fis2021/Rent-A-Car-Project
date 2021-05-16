package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Date;
import java.util.LinkedList;

public class CarView extends Car
{
    private ImageView image;

    public CarView(int id, String marca, int kilometri, String oras, int pret, String consum, double rating, int numberOfRates, String imagePath, boolean isAvailable, Date rentDate, int rentInterval, LinkedList<String> usersWhoGaveFeedback)
    {
        super(id, marca, kilometri, oras, pret, consum, rating, numberOfRates, imagePath, isAvailable, rentDate, rentInterval, usersWhoGaveFeedback);
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
