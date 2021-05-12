package services;

import exceptions.CarDoesNotExistException;
import models.Car;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.LinkedList;


import static services.FileSystemService.getPathToFile;

public class CarService
{
    private static ObjectRepository<Car> carRepository;

    public static void initDatabase()
    {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("car_database.db").toFile())
                .openOrCreate("test", "test");

        carRepository = database.getRepository(Car.class);
    }

    public static void addCar(String marca, int kilometri, String oras, int rating, String imagePath)
    {
        carRepository.insert(new Car(getLastId() + 1, marca, kilometri, oras, rating, imagePath));
    }

    public static LinkedList<String> getOrase()
    {
        LinkedList<String> orase = new LinkedList<String>();

        for (Car car : carRepository.find())
        {
            if (!orase.contains(car.getOras()))
            {
                orase.add(car.getOras());
            }
        }

        return orase;
    }

    public static LinkedList<String> getMarci()
    {
        LinkedList<String> marci = new LinkedList<String>();

        for (Car car : carRepository.find())
        {
            if (!marci.contains(car.getMarca()))
            {
                marci.add(car.getMarca());
            }
        }

        return marci;
    }

    public static LinkedList<Car> getCarsByFilter(String oras, String marca)
    {
        LinkedList<Car> cars = new LinkedList<Car>();

        for (Car car : carRepository.find())
        {
            if (car.getOras().equals(oras) && car.getMarca().equals(marca))
            {
                cars.add(car);
            }
        }

        return cars;
    }

    public static void deleteCarById(int id) throws CarDoesNotExistException
    {
        Car carToDelete = null;

        for (Car car : carRepository.find())
        {
            if (car.getId() == id)
            {
                carToDelete = car;
            }
        }

        if (carToDelete == null)
        {
            throw new CarDoesNotExistException();
        }

        carRepository.remove(carToDelete);
    }

    private static int getLastId()
    {
        var maxId = 0;

        for (Car car : carRepository.find())
        {
            if (car.getId() > maxId)
            {
                maxId = car.getId();
            }
        }

        return maxId;
    }
}
