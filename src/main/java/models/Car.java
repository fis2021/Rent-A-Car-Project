package models;

import org.dizitart.no2.objects.Id;

public class Car
{
    @Id
    private int id;
    private String marca;
    private int kilometri;
    private String oras;
    private int rating;
    private String imagePath;

    public Car(int id, String marca, int kilometri, String oras, int rating, String imagePath) {
        this.id = id;
        this.marca = marca;
        this.kilometri = kilometri;
        this.oras = oras;
        this.rating = rating;
        this.imagePath = imagePath;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating=rating;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
