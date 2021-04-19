package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import services.AlertService;
import services.LoginService;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private StackPane dashboardStackPane;

    @FXML
    private Button employeesButton;

    @FXML
    private Button wingsCellsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDefaultPane();
    }

    public void handleChangePane(ActionEvent actionEvent) {
        if (actionEvent.getSource() == employeesButton) {
            loadPane("/views/components/employeesPane.fxml");
        } else if (actionEvent.getSource() == wingsCellsButton) {
            loadPane("/views/components/wingsCellsPane.fxml");
        }
    }

    public void handleEditUser() {
        loadPane("/views/components/usersPane.fxml");
    }

    public void handleLogout() {
        LoginService.doLogout();
    }

    private void loadDefaultPane() {
        loadPane("/views/components/employeesPane.fxml");
    }

    private void loadPane(String fxmlSrc) {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource(fxmlSrc));

            dashboardStackPane.getChildren().setAll(pane);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }
}
