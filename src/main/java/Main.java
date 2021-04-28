import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.CarService;
import services.FileSystemService;
import services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        initDirectory();
        UserService.initDatabase();


        CarService.initDatabase();
        //CarService.addCar("bmw", 125000, "Timisoara", 10);
        //CarService.addCar("VW", 175000, "Timisoara", 10);
        //CarService.addCar("Ford", 323000, "Timisoara", 9);
        //CarService.addCar("VW", 285000, "Timisoara", 9);

        CarService.proba();



        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("Rent A Car");
        primaryStage.setScene(new Scene(root, 600, 460));
        primaryStage.show();
    }

    private void initDirectory()
    {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if (!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdirs();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}