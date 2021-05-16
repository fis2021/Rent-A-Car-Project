package tests;

import models.Car;
import org.junit.Assert;
import org.junit.Test;
import services.CarService;

import java.util.LinkedList;

class CarServiceTest
{
    @Test
    public void testGetCarsByFilter()
    {
        Car car = new Car(1000, "marca", 200000, "oras", 25, "5%", 9.5, 3, "Imageath", true, null, 0,  new LinkedList<String>());
        CarService.addCar(car.getMarca(), car.getKilometri(), car.getOras(), car.getPret(), car.getConsum(), car.getImagePath());

        LinkedList<Car> foundCars = CarService.getCarsByFilter(car.getOras(), car.getMarca(), 250000, 30);

        LinkedList<Car> expectedCarsToBeFound = new LinkedList<>();
        expectedCarsToBeFound.add(new Car(1000, "marca", 200000, "oras", 25, "5%", 9.5, 3, "Imageath", true, null, 0,  new LinkedList<String>()));

        Assert.assertSame(expectedCarsToBeFound,foundCars);
    }

    public void testGetOrase()
    {
        Car car1 = new Car(1000, "marca", 200000, "oras1", 25, "5%", 9.5, 3, "Imageath", true, null, 0,  new LinkedList<String>());
        Car car2 = new Car(1000, "marca", 200000, "oras2", 25, "5%", 9.5, 3, "Imageath", true, null, 0,  new LinkedList<String>());
        Car car3 = new Car(1000, "marca", 200000, "oras3", 25, "5%", 9.5, 3, "Imageath", true, null, 0,  new LinkedList<String>());
        CarService.addCar(car1.getMarca(), car1.getKilometri(), car1.getOras(), car1.getPret(), car1.getConsum(), car1.getImagePath());
        CarService.addCar(car2.getMarca(), car2.getKilometri(), car2.getOras(), car2.getPret(), car2.getConsum(), car2.getImagePath());
        CarService.addCar(car3.getMarca(), car3.getKilometri(), car3.getOras(), car3.getPret(), car3.getConsum(), car3.getImagePath());

        LinkedList<String> foundOrase = CarService.getOrase();
        LinkedList<String> expectedOrase = new LinkedList<String>();
        expectedOrase.add("oras1");
        expectedOrase.add("oras2");
        expectedOrase.add("oras3");

        Assert.assertSame(expectedOrase, foundOrase);
    }

    @Test
    public void testGetMarci()
    {
        Car car1 = new Car(1000, "marca1", 200000, "oras", 25, "5%", 9.5, 3, "Imageath", true, null, 0,  new LinkedList<String>());
        Car car2 = new Car(1000, "marca2", 200000, "oras", 25, "5%", 9.5, 3, "Imageath", true, null, 0,  new LinkedList<String>());
        Car car3 = new Car(1000, "marca3", 200000, "oras", 25, "5%", 9.5, 3, "Imageath", true, null, 0,  new LinkedList<String>());
        CarService.addCar(car1.getMarca(), car1.getKilometri(), car1.getOras(), car1.getPret(), car1.getConsum(), car1.getImagePath());
        CarService.addCar(car2.getMarca(), car2.getKilometri(), car2.getOras(), car2.getPret(), car2.getConsum(), car2.getImagePath());
        CarService.addCar(car3.getMarca(), car3.getKilometri(), car3.getOras(), car3.getPret(), car3.getConsum(), car3.getImagePath());

        LinkedList<String> foundMarci = CarService.getMarci();
        LinkedList<String> expectedMarci = new LinkedList<String>();
        expectedMarci.add("marca1");
        expectedMarci.add("marca2");
        expectedMarci.add("marca3");

        Assert.assertSame(expectedMarci, foundMarci);
    }

    @Test
    public void testGetLastId()
    {
        int oldLastId = CarService.getLastId();

        Car car = new Car(1000, "marca", 200000, "oras", 25, "5%", 9.5, 3, "Imageath", true, null, 0,  new LinkedList<String>());
        CarService.addCar(car.getMarca(), car.getKilometri(), car.getOras(), car.getPret(), car.getConsum(), car.getImagePath());

        int newLastId = CarService.getLastId();

        Assert.assertEquals(oldLastId+1, newLastId);
    }
}