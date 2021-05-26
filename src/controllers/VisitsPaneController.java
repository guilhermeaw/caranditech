package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Visit;

import java.net.URL;
import java.util.ResourceBundle;

public class VisitsPaneController implements Initializable {
    @FXML
    private TableView<Visit> visitsTable;

    @FXML
    private TableColumn<Visit, String> scheduleDateColumn;

    @FXML
    private TableColumn<Visit, String> prisonerColumn;

    @FXML
    private TableColumn<Visit, String> visitorColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        visitsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
    }

    public void refreshContent() {

    }

    public void handleAddVisit() {}

    public void handleEditVisit() {}

    public void handleDeleteVisit() {}
}
