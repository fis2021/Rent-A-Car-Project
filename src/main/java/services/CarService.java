package services;

import exceptions.CarDoesNotExistException;
import models.Car;
import models.CarView;
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

    public static void addCar(String marca, int kilometri, String oras, String imagePath)
    {
        carRepository.insert(new Car(getLastId() + 1, marca, kilometri, oras, 0.0, 0, imagePath, new LinkedList<String>()));
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

    public static LinkedList<Car> getCarsByFilter(String oras, String marca, int km)
    {
        LinkedList<Car> cars = new LinkedList<Car>();

        for (Car car : carRepository.find())
        {
            if ((car.getOras().equals(oras) || oras.equals("All")) && (car.getMarca().equals(marca) || marca.equals("All")) && car.getKilometri() <= km)
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

    public static void updateDataBase(CarView carToUpdate)
    {
        for (Car car : carRepository.find())
        {
            if (car.getId() == carToUpdate.getId())
            {
                car.setRating(carToUpdate.getRating());
                car.setNumberOfRates(carToUpdate.getNumberOfRates());
                car.setUsersWhoGaveFeedback(carToUpdate.getUsersWhoGaveFeedback());
                carRepository.update(car);
                return;
            }
        }
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
