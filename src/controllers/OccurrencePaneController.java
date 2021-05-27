package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Occurrence;

import java.net.URL;
import java.util.ResourceBundle;

public class OccurrencePaneController implements Initializable {
    @FXML
    private TableView<Occurrence> occurrencesTable;

    @FXML
    private TableColumn<Occurrence, String> titleColumn;

    @FXML
    private TableColumn<Occurrence, String> prisonerColumn;

    @FXML
    private TableColumn<Occurrence, String> authorColumn;

    @FXML
    private TableColumn<Occurrence, String> infoColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        occurrencesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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

    public void handleAddOccurrence() {}

    public void handleEditOccurrence() {}

    public void handleDeleteOccurrence() {}
}
