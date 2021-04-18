package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private StackPane dashboardStackPane;

    @FXML
    private Button usersButton;

    @FXML
    private Button employeesButton;

    @FXML
    private Button wingsCellsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDefaultPane();
    }

    public void handleChangePane(ActionEvent actionEvent) {
        if (actionEvent.getSource() == usersButton) {
            loadPane("/views/components/usersPane.fxml");
        }
    }

    private void loadDefaultPane() {
        loadPane("/views/components/usersPane.fxml");
    }

    private void loadPane(String fxmlSrc) {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource(fxmlSrc));

            dashboardStackPane.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Erro ao trocar painel do dashboard: " + e.getMessage());
        }
    }
}
