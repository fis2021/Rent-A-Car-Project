package controllers;

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
import exceptions.UsernameDoesNotExistException;
import exceptions.WrongPasswordException;
import exceptions.WrongRoleException;
import services.UserService;

public class LoginController {

    @FXML
    private Text loginUsernameMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox role;

    @FXML
    public void initialize() {
        role.getItems().addAll("Client", "Admin");
    }

    private String userRole;
    private static String loggedUser;

    @FXML
    public void handleLoginAction(javafx.event.ActionEvent MainPage) throws Exception {
        try {
            UserService.checkUserCredentials(usernameField.getText(), passwordField.getText(), (String) role.getValue());
            Parent mainPage = FXMLLoader.load(getClass().getClassLoader().getResource("main_page.fxml"));
            Stage window = (Stage) ((Node) MainPage.getSource()).getScene().getWindow();
            window.setTitle("Rent a Car");
            window.setScene(new Scene(mainPage, 600, 460));
            window.show();

        }
        /*
        catch (UsernameDoesNotExistException e) {
            loginUsernameMessage.setText(e.getMessage());
        } catch (WrongRoleException e){
            loginUsernameMessage.setText((e.getMessage()));
        } catch (WrongPasswordException e){
            loginUsernameMessage.setText(e.getMessage());
        }
         */
        catch (Exception e)
        {
            loginUsernameMessage.setText(e.getMessage());
        }
    }
    public void goBackToRegisterScene(javafx.event.ActionEvent login)throws Exception{
        Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();;
        window.setTitle("Registration");
        window.setScene(new Scene(root1, 600, 460));
        window.show();
    }
}