package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.PrisonerType;

import java.net.URL;
import java.util.ResourceBundle;

public class PrisonerTypesPaneController implements Initializable {
    @FXML
    private TableView<PrisonerType> prisonerTypesTable;

    @FXML
    private TableColumn<PrisonerType, String> profitColumn;

    @FXML
    private TableColumn<PrisonerType, String> infoColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        prisonerTypesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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

    public void handleAddPrisonerType() {}

    public void handleEditPrisonerType() {}

    public void handleDeletePrisonerType() {}
}
