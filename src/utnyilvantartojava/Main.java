package utnyilvantartojava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        String title="Útnyilvántartó " ;

        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root, 1190, 750));
        primaryStage.getScene().getStylesheets().add("style.css");
        primaryStage.getIcons().add(new Image("file:utnyilv.png"));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
      launch(args);
    }
}
