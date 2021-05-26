package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Cell;

import java.net.URL;
import java.util.ResourceBundle;

public class CellsPaneController implements Initializable {
    @FXML
    private TableView<Cell> cellsTable;

    @FXML
    private TableColumn<Cell, String> nameColumn;

    @FXML
    private TableColumn<Cell, String> wingColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        cellsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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

    public void handleAddCell() {}

    public void handleEditCell() {}

    public void handleDeleteCell() {}
}
