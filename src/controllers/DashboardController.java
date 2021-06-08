package controllers;

import common.Tooltip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import services.LoginService;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private StackPane dashboardStackPane;

    @FXML
    private Button btnEditUser;

    @FXML
    private Button btnLogout;

    @FXML
    private Button prisonersButton;

    @FXML
    private Button employeesButton;

    @FXML
    private Button wingsCellsButton;

    @FXML
    private Button visitorsButton;

    @FXML
    private Button visitsButton;

    @FXML
    private Button occurrencesButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTooltips();
        loadDefaultPane();
    }

    public void handleChangePane(ActionEvent actionEvent) {
        if (actionEvent.getSource() == employeesButton) {
            loadPane("/views/components/employeesOccupationsTabbedPane.fxml");
        } else if (actionEvent.getSource() == wingsCellsButton) {
            loadPane("/views/components/wingsCellsTabbedPane.fxml");
        } else if (actionEvent.getSource() == prisonersButton) {
            loadPane("/views/components/prisonersTabbedPane.fxml");
        } else if (actionEvent.getSource() == visitorsButton) {
            loadPane("/views/components/visitorsPane.fxml");
        } else if (actionEvent.getSource() == visitsButton) {
            loadPane("/views/components/visitsPane.fxml");
        } else if (actionEvent.getSource() == occurrencesButton) {
            loadPane("/views/components/occurrencesTypesTabbedPane.fxml");
        }
    }

    public void handleEditUser() {
        loadPane("/views/components/usersPane.fxml");
    }

    public void handleLogout() {
        LoginService.doLogout();
    }

    private void loadTooltips() {
        btnEditUser.setTooltip(new Tooltip("Editar usuário"));
        btnLogout.setTooltip(new Tooltip("Efetuar logout"));
    }

    private void loadDefaultPane() {
        loadPane("/views/components/employeesOccupationsTabbedPane.fxml");
    }

    private void loadPane(String fxmlSrc) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(fxmlSrc));

            dashboardStackPane.getChildren().setAll(pane);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }
}
