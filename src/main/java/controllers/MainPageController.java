package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Car;
import models.CarView;
import services.CarService;

import java.util.LinkedList;

public class MainPageController
{
    @FXML
    private ChoiceBox orase;

    @FXML
    private ChoiceBox marci;

    @FXML
    private TextField kilometri;

    @FXML private TableView<CarView> tableView;

    @FXML
    public void initialize()
    {
        orase.getItems().addAll(CarService.getOrase());
        orase.getSelectionModel().selectFirst();

        marci.getItems().addAll(CarService.getMarci());
        marci.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleSearchAction()
    {
        ObservableList<CarView> data = tableView.getItems();
        LinkedList<Car> cars = CarService.getCarsByFilter(orase.getSelectionModel().getSelectedItem().toString(), marci.getSelectionModel().getSelectedItem().toString());

        for (Car car : cars)
        {
            CarView carView = new CarView(car.getId(), car.getMarca(), car.getKilometri(), car.getOras(), car.getRating(), car.getImagePath());
            data.add(carView);
        }
    }
}
