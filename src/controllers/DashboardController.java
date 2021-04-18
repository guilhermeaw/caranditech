package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class DashboardController {
    @FXML
    private StackPane dashboardStackPane;

    @FXML
    private Button usersButton;

    @FXML
    private Button employeesButton;

    @FXML
    private Button wingsCellsButton;

    public DashboardController() {
    }

    public void handleChangePane(ActionEvent actionEvent) {
        if (actionEvent.getSource() == usersButton) {
            loadPane("usersPane.fxml");
        }
    }

    private void loadPane(String fxmlSrc) {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource(fxmlSrc));

        } catch (Exception e) {
            System.out.println("Erro ao trocar painel do dashboard");
        }
    }
}
