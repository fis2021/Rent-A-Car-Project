package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PaymentController
{
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
    public void initialize()
    {
        sumaDePlata.setDisable(true);
        luni.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");

        for (int i = 2021; i <= 2031; i++)
        {
            ani.getItems().add(String.valueOf(i));
        }

        cuFaraSoferLabel.setTooltip(new Tooltip("Optiunea cu sofer va adauga 50 de euro la suma finala de plata"));
        cuSofer.setTooltip(new Tooltip("Optiunea cu sofer va adauga 50 de euro la suma finala de plata"));
    }

    @FXML
    public void handlePayAction()
    {

    }
}
