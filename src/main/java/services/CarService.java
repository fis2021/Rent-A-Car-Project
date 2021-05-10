package services;

import models.Car;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;


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

    public static void addCar(String marca, int kilometri, String oras, int rating)
    {
        carRepository.insert(new Car(marca, kilometri, oras, rating));
    }

    public static void proba()
    {
        for (Car car : carRepository.find())
        {
            System.out.println(car.toString());
        }
    }
}
