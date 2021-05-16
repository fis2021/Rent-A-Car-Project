package controllers;

import exceptions.CannotBlockAdminException;
import exceptions.CarDoesNotExistException;
import exceptions.EmailDoesNotExistException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Car;
import models.CarView;
import services.CarService;
import services.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    private TextField pret;

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
    private TextField pretDeAdaugat;

    @FXML
    private TextField consumDeAdaugat;

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
    private TextField emailDeSters;

    @FXML
    private Button butonBlocare;

    @FXML
    private Label warning;

    @FXML
    private TextField rating;

    @FXML
    private Button rateButton;

    @FXML
    private Button rentButton;

    @FXML
    public void initialize()
    {
        orase.getItems().add("All");
        orase.getItems().addAll(CarService.getOrase());
        orase.getSelectionModel().selectFirst();

        marci.getItems().add("All");
        marci.getItems().addAll(CarService.getMarci());
        marci.getSelectionModel().selectFirst();

        boolean isAdmin = UserService.getActiveUser().getRole().equals("Admin");
        doarAdminAdaugare.setVisible(isAdmin);
        doarAdminStergere.setVisible(isAdmin);

        pozaDeAdaugat.setDisable(true);

        boolean isAddButtonDisabled =
                marcaDeAdaugat.getText().isBlank() ||
                        orasDeAdaugat.getText().isBlank() ||
                        kmDeAdaugat.getText().isBlank() ||
                        pozaDeAdaugat.getText().isBlank() ||
                        pretDeAdaugat.getText().isBlank() ||
                        consumDeAdaugat.getText().isBlank();
        butonAdaugare.setDisable(isAddButtonDisabled);
        butonStergere.setDisable(idDeSters.getText().isBlank());

        boolean isRateButtonDisabled = rating.getText().isBlank() || (tableView.getSelectionModel().getSelectedItem() != null);
        rateButton.setDisable(isRateButtonDisabled);

        rentButton.setDisable(tableView.getSelectionModel().getSelectedItem() == null);

        butonBlocare.setDisable(emailDeSters.getText().isBlank());

        CarService.checkIfRentHasExpired();
    }

    public void keyReleased()
    {
        boolean isDisabled =
                marcaDeAdaugat.getText().isBlank() ||
                        orasDeAdaugat.getText().isBlank() ||
                        kmDeAdaugat.getText().isBlank() ||
                        pozaDeAdaugat.getText().isBlank() ||
                        pretDeAdaugat.getText().isBlank() ||
                        consumDeAdaugat.getText().isBlank();
        butonAdaugare.setDisable(isDisabled);
    }

    public void keyReleasedId()
    {
        butonStergere.setDisable(idDeSters.getText().isBlank());
    }

    public void keyReleasedEmail() { butonBlocare.setDisable(emailDeSters.getText().isBlank());}

    public void ratingReleasedOrCarSelected()
    {
        boolean isDisabled = rating.getText().isBlank() || (tableView.getSelectionModel().getSelectedItem() == null);
        rateButton.setDisable(isDisabled);
        rentButton.setDisable(tableView.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    public void handleSearchAction()
    {
        ObservableList<CarView> data = tableView.getItems();

        int km = 0;
        int price = 0;

        try
        {
            km = Integer.parseInt(kilometri.getText());
        }
        catch (Exception e)
        {
            warning.setText("Campul \"Km\" este invalid!");
            return;
        }

        try
        {
            price = Integer.parseInt(pret.getText());
        }
        catch (Exception e)
        {
            warning.setText("Campul \"Pret\" este invalid!");
            return;
        }

        if (km < 0)
        {
            warning.setText("Numarul de km trebuie sa fie > 0");
            return;
        }

        if (price < 0)
        {
            warning.setText("Pretul trebuie sa fie > 0");
            return;
        }

        LinkedList<Car> cars = CarService.getCarsByFilter(
                orase.getSelectionModel().getSelectedItem().toString(),
                marci.getSelectionModel().getSelectedItem().toString(),
                km, price);

        data.clear();
        for (Car car : cars)
        {
            CarView carView = new CarView(car.getId(), car.getMarca(), car.getKilometri(), car.getOras(), car.getPret(), car.getConsum(), car.getRating(), car.getNumberOfRates(), car.getImagePath(), car.getIsAvailable(), car.getRentDate(), car.getRentInterval(), car.getUsersWhoGaveFeedback());
            data.add(carView);
        }

        boolean isDisabled = rating.getText().isBlank() || (tableView.getSelectionModel().getSelectedItem() == null);
        rateButton.setDisable(isDisabled);
    }

    @FXML
    public void handleAddCar()
    {
        int kilometri;

        try
        {
            kilometri = Integer.parseInt(kmDeAdaugat.getText());
        }
        catch (Exception e1)
        {
            warning.setText("Kilometrii introdusi nu sunt valizi!");
            return;
        }

        if (kilometri < 0)
        {
            warning.setText("Kilometrii introdusi trebuie sa fie >0!");
            return;
        }

        int price;

        try
        {
            price = Integer.parseInt(pretDeAdaugat.getText());
        }
        catch (Exception e2)
        {
            warning.setText("Pretul introdus nu este valid!");
            return;
        }

        if (price <= 0)
        {
            warning.setText("Pretul introdus trebuie sa fie >0!");
            return;
        }

        CarService.addCar(marcaDeAdaugat.getText(), kilometri, orasDeAdaugat.getText(), price, consumDeAdaugat.getText(), pozaDeAdaugat.getText());

        orase.getItems().clear();
        orase.getItems().add("All");
        orase.getItems().addAll(CarService.getOrase());
        orase.getSelectionModel().selectFirst();

        marci.getItems().clear();
        marci.getItems().add("All");
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
    public void handleBlockUser()
    {
        try
        {
            UserService.blockUser(emailDeSters.getText());
        }
        catch (EmailDoesNotExistException | CannotBlockAdminException e)
        {
            warning.setText(e.getMessage());
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

        boolean isDisabled =
                marcaDeAdaugat.getText().isBlank() ||
                        orasDeAdaugat.getText().isBlank() ||
                        kmDeAdaugat.getText().isBlank() ||
                        pozaDeAdaugat.getText().isBlank() ||
                        pretDeAdaugat.getText().isBlank() ||
                        consumDeAdaugat.getText().isBlank();
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

        if (selectedCar.getUsersWhoGaveFeedback().contains(UserService.getActiveUser().getCnp())) {
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

        selectedCar.addUserWhoGaveFeedback(UserService.getActiveUser().getCnp());

        // refresh list after rating
        ObservableList<CarView> data = tableView.getItems();

        data.remove(selectedCar);
        data.add(selectedCar);

        CarService.updateDataBase(selectedCar);
    }

    @FXML
    public void handleRentAction(javafx.event.ActionEvent MainPage) throws Exception
    {
        CarService.lastSelectedCar = tableView.getSelectionModel().getSelectedItem();
        Parent mainPage = FXMLLoader.load(getClass().getClassLoader().getResource("payment.fxml"));
        Stage window = (Stage) ((Node) MainPage.getSource()).getScene().getWindow();
        window.setTitle("Payment");
        window.setScene(new Scene(mainPage, 640, 480));
        window.show();
    }
}
