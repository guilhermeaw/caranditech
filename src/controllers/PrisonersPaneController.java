package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Prisoner;

import java.net.URL;
import java.util.ResourceBundle;

public class PrisonersPaneController implements Initializable {
    @FXML
    private TableView<Prisoner> prisonersTable;

    @FXML
    private TableColumn<Prisoner, String> nameColumn;

    @FXML
    private TableColumn<Prisoner, String> prisonerTypeColumn;

    @FXML
    private TableColumn<Prisoner, String> cellColumn;

    @FXML
    private TableColumn<Prisoner, String> enterDateColumn;

    @FXML
    private TableColumn<Prisoner, String> exitDateColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        prisonersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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

    public void handleAddPrisoner() {}

    public void handleEditPrisoner() {}

    public void handleDeletePrisoner() {}
}
