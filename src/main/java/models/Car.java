package models;

import org.dizitart.no2.objects.Id;

import java.util.Date;
import java.util.LinkedList;

public class Car
{
    @Id
    private int id;
    private String marca;
    private int kilometri;
    private String oras;
    private int pret;
    private String consum;
    private double rating;
    private int numberOfRates;
    private String imagePath;
    private boolean isAvailable;
    private String availability;
    private Date rentDate;
    private int rentInterval;
    protected LinkedList<String> usersWhoGaveFeedback;

    public Car(int id, String marca, int kilometri, String oras, int pret, String consum, double rating, int numberOfRates, String imagePath, boolean isAvailable, Date rentDate, int rentInterval, LinkedList<String> usersWhoGaveFeedback) {
        this.id = id;
        this.marca = marca;
        this.kilometri = kilometri;
        this.oras = oras;
        this.pret = pret;
        this.consum = consum;
        this.rating = rating;
        this.numberOfRates = numberOfRates;
        this.imagePath = imagePath;
        this.isAvailable = isAvailable;
        this.availability = isAvailable ? "Available" : "Unavailable";
        this.rentDate = rentDate;
        this.rentInterval = rentInterval;
        this.usersWhoGaveFeedback = usersWhoGaveFeedback;
    }

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getKilometri() {
        return kilometri;
    }

    public void setKilometri(int kilometri) {
        this.kilometri = kilometri;
    }

    public String getOras() { return oras; }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public int getPret() { return pret;}

    public void setPret(int pret) {this.pret = pret;}

    public String getConsum() { return consum; }

    public void setConsum() { this.consum = consum;}

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating=rating;
    }

    public void setNumberOfRates(int numberOfRates) {
        this.numberOfRates=numberOfRates;
    }

    public int getNumberOfRates() {
        return numberOfRates;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean getIsAvailable() {return isAvailable;}

    public void setIsAvailable(boolean isAvailable)
    {
        this.isAvailable = isAvailable;
        availability = isAvailable ? "Available" : "Unavailable";
    }

    public String getAvailability() {return this.availability;}

    public void setAvailability(String availability) {this.availability = availability;}

    public Date getRentDate() { return this.rentDate; }

    public void setRentDate(Date rentDate) { this.rentDate = rentDate; }

    public int getRentInterval() { return this.rentInterval; }

    public void setRentInterval(int rentInterval) { this.rentInterval = rentInterval; }

    public LinkedList<String> getUsersWhoGaveFeedback() {
        return usersWhoGaveFeedback;
    }

    public void setUsersWhoGaveFeedback (LinkedList<String> usersWhoGaveFeedback) {
        this.usersWhoGaveFeedback = usersWhoGaveFeedback;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Car)
        {
            Car car = (Car) o;

            if (car.marca.equals(this.marca) &&
                car.kilometri == this.kilometri &&
                car.oras.equals(this.oras))
            {
                return true;
            }

            return false;
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "Masina: " + this.id + " " + this.marca + " " + this.kilometri + " " + this.oras;
    }
}
