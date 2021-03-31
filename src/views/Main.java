package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    private double x;
    private double y;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("CarandiTech");

        root.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - x);
            primaryStage.setY(mouseEvent.getScreenY() - y);
        });

        primaryStage.show();
    }

    public void changeScene(String fxmlResource) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxmlResource));
        stage.getScene().setRoot(pane);
    }
}
