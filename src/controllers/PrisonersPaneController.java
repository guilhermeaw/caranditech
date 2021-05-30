package controllers;

import common.EditorCallback;
import db.managers.CellManager;
import db.managers.PrisonerManager;
import db.managers.PrisonerTypeManager;
import editors.PrisonerEditor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Cell;
import models.Prisoner;
import models.PrisonerType;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
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
        try {
            List<Prisoner> prisoners = PrisonerManager.getInstance().getAll();

            ObservableList<Prisoner> prisonerObservableList = FXCollections.observableArrayList(prisoners);

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            prisonerTypeColumn.setCellValueFactory(column -> {
                PrisonerType prisonerType = getPrisonerTypeById(column.getValue().getPrisonerTypeId());

                return new SimpleStringProperty(prisonerType != null ? prisonerType.getName() : "n/d");
            });
            cellColumn.setCellValueFactory(column -> {
                Cell cell = getCellById(column.getValue().getCellId());

                return new SimpleStringProperty(cell != null ? cell.getName() : "n/d");
            });
            enterDateColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getEnterDate().toString()));
            exitDateColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getExitDate().toString()));

            prisonersTable.setItems(prisonerObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddPrisoner() {
        new PrisonerEditor(new EditorCallback<Prisoner>(new Prisoner()) {
            @Override
            public void onEvent() {
                try {
                    PrisonerManager.getInstance().create((Prisoner) getSource());

                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    public void handleEditPrisoner() {}

    public void handleDeletePrisoner() {}

    private PrisonerType getPrisonerTypeById(int id) {
        try {
            return PrisonerTypeManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }

    private Cell getCellById(int id) {
        try {
            return CellManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }
}
