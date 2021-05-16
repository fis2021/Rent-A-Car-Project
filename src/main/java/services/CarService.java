package services;

import exceptions.CarDoesNotExistException;
import models.Car;
import models.CarView;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.Date;
import java.util.LinkedList;


import static services.FileSystemService.getPathToFile;

public class CarService
{
    private static ObjectRepository<Car> carRepository;
    public static Car lastSelectedCar;

    public static void initDatabase()
    {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("car_database.db").toFile())
                .openOrCreate("test", "test");

        carRepository = database.getRepository(Car.class);
    }

    public static void addCar(String marca, int kilometri, String oras, int pret, String consum, String imagePath)
    {
        carRepository.insert(new Car(getLastId() + 1, marca, kilometri, oras, pret, consum, 0.0, 0, imagePath, true, null, 0, new LinkedList<String>()));
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

    public static LinkedList<Car> getCarsByFilter(String oras, String marca, int km, int pret)
    {
        LinkedList<Car> cars = new LinkedList<Car>();

        for (Car car : carRepository.find())
        {
            if ((car.getOras().equals(oras) || oras.equals("All")) && (car.getMarca().equals(marca) || marca.equals("All")) && car.getKilometri() <= km && car.getPret() <= pret)
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

    public static void updateDataBase(Car carToUpdate)
    {
        for (Car car : carRepository.find())
        {
            if (car.getId() == carToUpdate.getId())
            {
                car.setRating(carToUpdate.getRating());
                car.setNumberOfRates(carToUpdate.getNumberOfRates());
                car.setUsersWhoGaveFeedback(carToUpdate.getUsersWhoGaveFeedback());
                car.setIsAvailable(carToUpdate.getIsAvailable());
                car.setAvailability(carToUpdate.getAvailability());
                carRepository.update(car);
                return;
            }
        }
    }

    public static void checkIfRentHasExpired()
    {
        for (Car car : carRepository.find())
        {
            if (car.getIsAvailable() == false && car.getRentDate() != null)
            {
                // daca ziua inchirierii este inaintea zilei curente minus numarul de zile de inchiriere
                // numarul de zile de inchiriere * 86400000 = pe cate milisecunde s-a inchiriat masina
                if (car.getRentDate().compareTo(new Date(System.currentTimeMillis() - car.getRentInterval()*86400000)) <= 0)
                {
                    car.setRentDate(null);
                    car.setRentInterval(0);
                    car.setIsAvailable(true);
                }
            }
        }
    }

    public static int getLastId()
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
