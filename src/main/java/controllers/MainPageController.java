package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import models.Car;
import models.CarView;
import services.CarService;
import services.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

public class MainPageController
{
    @FXML
    private ChoiceBox orase;

    @FXML
    private ChoiceBox marci;

    @FXML
    private TextField kilometri;

    @FXML
    private TableView<CarView> tableView;

    @FXML
    private GridPane doarAdminAdaugare;

    @FXML
    private GridPane doarAdminStergere;

    @FXML
    private TextField marcaDeAdaugat;

    @FXML
    private TextField orasDeAdaugat;

    @FXML
    private TextField kmDeAdaugat;

    @FXML
    private TextField pozaDeAdaugat;

    @FXML
    public void initialize()
    {
        orase.getItems().addAll(CarService.getOrase());
        orase.getSelectionModel().selectFirst();

        marci.getItems().addAll(CarService.getMarci());
        marci.getSelectionModel().selectFirst();

        boolean isAdmin = UserService.getActiveUserRole().equals("Admin");
        doarAdminAdaugare.setVisible(isAdmin);
        doarAdminStergere.setVisible(isAdmin);
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

    @FXML
    public void handleAddCar()
    {
        int kilometri = Integer.parseInt(kmDeAdaugat.getText());

        CarService.addCar(marcaDeAdaugat.getText(), kilometri, orasDeAdaugat.getText(), 0, pozaDeAdaugat.getText());

        orase.getItems().clear();
        orase.getItems().addAll(CarService.getOrase());
        orase.getSelectionModel().selectFirst();

        marci.getItems().clear();
        marci.getItems().addAll(CarService.getMarci());
        marci.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleBrowseForPicture() throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        File pictureFile = fileChooser.showOpenDialog(orasDeAdaugat.getScene().getWindow());

        Path temp = Files.copy(pictureFile.toPath(), Paths.get(System.getProperty("user.dir"), "src\\main\\resources", pictureFile.getName()), StandardCopyOption.REPLACE_EXISTING);

        pozaDeAdaugat.setText(pictureFile.getName());
    }
}
