package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.CarService;

import java.io.IOException;

public class PaymentController
{
    @FXML
    private TextField numarDeZile;

    @FXML
    private TextField numarCard;

    @FXML
    private TextField numeDetinatorCard;

    @FXML
    private TextField cvc;

    @FXML
    private TextField sumaDePlata;

    @FXML
    private ChoiceBox luni;

    @FXML
    private ChoiceBox ani;

    @FXML
    private Label cuFaraSoferLabel;

    @FXML
    private CheckBox cuSofer;

    @FXML
    private Button payButton;

    @FXML
    private Label warning;

    @FXML
    public void initialize()
    {
        sumaDePlata.setDisable(true);

        for (int i = 1; i <= 12; i++)
        {
            luni.getItems().add(String.valueOf(i));
        }

        for (int i = 2021; i <= 2031; i++)
        {
            ani.getItems().add(String.valueOf(i));
        }

        cuFaraSoferLabel.setTooltip(new Tooltip("Optiunea cu sofer va adauga 50 de euro la suma finala de plata"));
        cuSofer.setTooltip(new Tooltip("Optiunea cu sofer va adauga 50 de euro la suma finala de plata"));
    }

    @FXML
    public void handlePayAction(javafx.event.ActionEvent MainPage) throws IOException
    {
        String inputErrors = validateInputs();
        if (!inputErrors.equals(""))
        {
            warning.setText(inputErrors);
            return;
        }

        Parent mainPage = FXMLLoader.load(getClass().getClassLoader().getResource("main_page.fxml"));
        Stage window = (Stage) ((Node) MainPage.getSource()).getScene().getWindow();
        window.setTitle("Rent a Car");
        window.setScene(new Scene(mainPage, 1024, 600));
        window.show();
    }

    private String validateInputs()
    {
        int nrZile;

        try
        {
            nrZile = Integer.parseInt(numarDeZile.getText());
        }
        catch (Exception e)
        {
            return "Numarul de zile este invalid";
        }

        if (nrZile <= 0)
        {
            return "Numarul de zile trebuie sa fie > 0";
        }

        if (numarCard.getText().trim().length() != 16)
        {
            return "Numarul de card trebuie sa contina 16 cifre";
        }

        if (!numarCard.getText().trim().matches("[0-9]{16}"))
        {
            return "Numarul de card trebuie sa contina doar cifre";
        }

        if (numeDetinatorCard.getText().isBlank())
        {
            return "Campul numelui detinatorului cardului este obligatoriu";
        }

        if (luni.getSelectionModel().getSelectedItem() == null)
        {
            return "Campul pentru luna expirarii cardului este obligatoriu";
        }

        if (ani.getSelectionModel().getSelectedItem() == null)
        {
            return "Campul pentru anul expirarii cardului este obligatoriu";
        }

        if (cvc.getText().trim().length() != 3)
        {
            return "CVC/CVV2 trebuie sa contina 3 cifre";
        }

        if (!cvc.getText().trim().matches("[0-9]{3}"))
        {
            return "CVC/CVV2 trebuie sa contina doar cifre";
        }

        return "";
    }

    public void nrZileSauSoferUpdated()
    {
        int nrZile;

        try
        {
            nrZile = Integer.parseInt(numarDeZile.getText());
        }
        catch (Exception e)
        {
            return;
        }

        if (nrZile <= 0)
        {
            return;
        }

        int suma = CarService.selectedCar.getPret() * nrZile + (cuSofer.isSelected() ? 50 : 0);
        sumaDePlata.setText(String.valueOf(suma));
    }
}
