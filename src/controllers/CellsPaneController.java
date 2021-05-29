package controllers;

import db.managers.CellManager;
import db.managers.WingManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Cell;
import models.Wing;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
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
        try {
            List<Cell> cells = CellManager.getInstance().getAll();

            ObservableList<Cell> cellsObservableList = FXCollections.observableArrayList(cells);

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            wingColumn.setCellValueFactory(column -> {
                Cell cell = column.getValue();
                Wing wing = getWingById(cell.getWingId());

                return new SimpleStringProperty(wing != null ? wing.getName() : "n/d");
            });

            cellsTable.setItems(cellsObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddCell() {}

    public void handleEditCell() {}

    public void handleDeleteCell() {}

    private Wing getWingById(int id) {
        try {
            return WingManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }
}
