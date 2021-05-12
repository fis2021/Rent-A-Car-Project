package controllers;

import exceptions.CarDoesNotExistException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
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
    private Button butonAdaugare;

    @FXML
    private TextField idDeSters;

    @FXML
    private Button butonStergere;

    @FXML
    private Label warning;

    @FXML
    private TextField rating;

    @FXML
    private Button rateButton;

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

        pozaDeAdaugat.setDisable(true);

        boolean isAddButtonDisabled = marcaDeAdaugat.getText().isBlank() || orasDeAdaugat.getText().isBlank() || kmDeAdaugat.getText().isBlank() || pozaDeAdaugat.getText().isBlank();
        butonAdaugare.setDisable(isAddButtonDisabled);
        butonStergere.setDisable(idDeSters.getText().isBlank());

        boolean isRateButtonDisabled = rating.getText().isBlank() || (tableView.getSelectionModel().getSelectedItem() != null);
        rateButton.setDisable(isRateButtonDisabled);
    }

    public void keyReleased()
    {
        boolean isDisabled = marcaDeAdaugat.getText().isBlank() || orasDeAdaugat.getText().isBlank() || kmDeAdaugat.getText().isBlank() || pozaDeAdaugat.getText().isBlank();
        butonAdaugare.setDisable(isDisabled);
    }

    public void keyReleasedId()
    {
        butonStergere.setDisable(idDeSters.getText().isBlank());
    }

    public void ratingReleasedOrCarSelected()
    {
        boolean isDisabled = rating.getText().isBlank() || (tableView.getSelectionModel().getSelectedItem() == null);
        rateButton.setDisable(isDisabled);
    }

    @FXML
    public void handleSearchAction()
    {
        ObservableList<CarView> data = tableView.getItems();
        LinkedList<Car> cars = CarService.getCarsByFilter(orase.getSelectionModel().getSelectedItem().toString(), marci.getSelectionModel().getSelectedItem().toString());

        data.clear();
        for (Car car : cars)
        {
            CarView carView = new CarView(car.getId(), car.getMarca(), car.getKilometri(), car.getOras(), car.getRating(), car.getNumberOfRates(), car.getImagePath(), car.getUsersWhoGaveFeedback());
            data.add(carView);
        }

        boolean isDisabled = rating.getText().isBlank() || (tableView.getSelectionModel().getSelectedItem() == null);
        rateButton.setDisable(isDisabled);
    }

    @FXML
    public void handleAddCar()
    {
        int kilometri = Integer.parseInt(kmDeAdaugat.getText());

        CarService.addCar(marcaDeAdaugat.getText(), kilometri, orasDeAdaugat.getText(), pozaDeAdaugat.getText());

        orase.getItems().clear();
        orase.getItems().addAll(CarService.getOrase());
        orase.getSelectionModel().selectFirst();

        marci.getItems().clear();
        marci.getItems().addAll(CarService.getMarci());
        marci.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleDeleteCar()
    {
        int id = -1;

        try
        {
            id = Integer.parseInt(idDeSters.getText());
        }
        catch (Exception e)
        {
            warning.setText("Id-ul " + idDeSters.getText() + " este invalid");
            return;
        }

        try
        {
            CarService.deleteCarById(id);
        }
        catch (CarDoesNotExistException ex)
        {
            warning.setText("Id-ul " + idDeSters.getText() + " nu exista");
        }
    }

    @FXML
    public void handleBrowseForPicture() throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        File pictureFile = fileChooser.showOpenDialog(orasDeAdaugat.getScene().getWindow());

        Files.copy(pictureFile.toPath(), Paths.get(System.getProperty("user.dir"), "src\\main\\resources", pictureFile.getName()), StandardCopyOption.REPLACE_EXISTING);

        pozaDeAdaugat.setText(pictureFile.getName());

        boolean isDisabled = marcaDeAdaugat.getText().isBlank() || orasDeAdaugat.getText().isBlank() || kmDeAdaugat.getText().isBlank() || pozaDeAdaugat.getText().isBlank();
        butonAdaugare.setDisable(isDisabled);
    }

    @FXML
    public void handleRateAction()
    {
        CarView selectedCar = tableView.getSelectionModel().getSelectedItem();
        int rate = 0;

        try
        {
            rate = Integer.parseInt(rating.getText());
        }
        catch (Exception e)
        {
            warning.setText("Invalid rating!");
            return;
        }

        if (rate < 0 || rate > 10)
        {
            warning.setText("Rating must be between 0 and 10!");
            return;
        }

        if (selectedCar.getUsersWhoGaveFeedback().contains(UserService.getActiveUserName())) {
            warning.setText("Car already rated by this user!");
            return;
        }

        if (selectedCar.getNumberOfRates() == 0)
        {
            selectedCar.setNumberOfRates(1);
            selectedCar.setRating(rate);
        }
        else
        {
            int oldNumberOfRates = selectedCar.getNumberOfRates();
            selectedCar.setNumberOfRates(oldNumberOfRates+1);
            int newNumberOfRates = selectedCar.getNumberOfRates();

            double oldRating = selectedCar.getRating();
            double newRating = oldRating + ((rate - oldRating)) / (double) newNumberOfRates;
            selectedCar.setRating(newRating);
        }

        selectedCar.addUserWhoGaveFeedback(UserService.getActiveUserName());

        // refresh list after rating
        ObservableList<CarView> data = tableView.getItems();
        data.remove(selectedCar);
        data.add(selectedCar);

        CarService.updateDataBase(selectedCar);
    }
}
