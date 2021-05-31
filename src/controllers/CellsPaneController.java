package controllers;

import common.EditorCallback;
import db.managers.CellManager;
import db.managers.WingManager;
import editors.CellEditor;
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
import services.AlertService;
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

    public void handleAddCell() {
        new CellEditor(new EditorCallback<Cell>(new Cell()) {
            @Override
            public void onEvent() {
                try {
                    CellManager.getInstance().create((Cell) getSource());

                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    public void handleEditCell() {
        Cell selectedCell = cellsTable.getSelectionModel().getSelectedItem();

        if (selectedCell != null) {
            new CellEditor(new EditorCallback<Cell>(selectedCell) {
                @Override
                public void onEvent() {
                    try {
                        CellManager.getInstance().update((Cell) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar uma cela");
        }
    }

    public void handleDeleteCell() {
        Cell selectedCell = cellsTable.getSelectionModel().getSelectedItem();

        if (selectedCell != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir a cela " + selectedCell.getName() + "?")) {
                try {
                    CellManager.getInstance().delete(selectedCell);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar uma cela");
        }
    }

    private Wing getWingById(int id) {
        try {
            return WingManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }
}
