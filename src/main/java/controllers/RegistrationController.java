package controllers;

import exceptions.EmailAlreadyExistsException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.UserService;

import java.io.IOException;

public class RegistrationController {

    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField cnpField;
    @FXML
    private ChoiceBox role;

    @FXML
    public void initialize() {
        role.getItems().addAll("Client", "Admin");
    }

    @FXML
    public void handleRegisterAction(javafx.event.ActionEvent login) throws IOException {
        try {
            UserService.addUser(
                    usernameField.getText(),
                    passwordField.getText(),
                    (String) role.getValue(),
                    emailField.getText(),
                    phoneNumberField.getText(),
                    addressField.getText(),
                    cnpField.getText());
            registrationMessage.setText("Account created successfully!");

            Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();;
            window.setTitle("Login");
            window.setScene(new Scene(root1, 600, 460));
            window.show();
        } catch (EmailAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
        }
    }
}
