package controllers;

import common.EditorCallback;
import db.managers.OccurrenceTypeManager;
import editors.OccurrenceTypeEditor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.OccurrenceType;
import services.AlertService;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OccurrenceTypesPaneController implements Initializable {
    @FXML
    private TableView<OccurrenceType> occurrenceTypesTable;

    @FXML
    private TableColumn<OccurrenceType, String> nameColumn;

    @FXML
    private TableColumn<OccurrenceType, String> infoColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        occurrenceTypesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            List<OccurrenceType> wings = OccurrenceTypeManager.getInstance().getAll();

            ObservableList<OccurrenceType> wingObservableList = FXCollections.observableArrayList(wings);

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            infoColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getDescription()));

            occurrenceTypesTable.setItems(wingObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddOccurrenceType() {
        new OccurrenceTypeEditor(new EditorCallback<OccurrenceType>(new OccurrenceType()) {
            @Override
            public void onEvent() {
                try {
                    OccurrenceTypeManager.getInstance().create((OccurrenceType) getSource());

                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    public void handleEditOccurrenceType() {
        OccurrenceType selectedOccurrenceType = occurrenceTypesTable.getSelectionModel().getSelectedItem();

        if (selectedOccurrenceType != null) {
            new OccurrenceTypeEditor(new EditorCallback<OccurrenceType>(selectedOccurrenceType) {
                @Override
                public void onEvent() {
                    try {
                        OccurrenceTypeManager.getInstance().update((OccurrenceType) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar um tipo de ocorrência");
        }
    }

    public void handleDeleteOccurrenceType() {
        OccurrenceType selectedOccurrenceType = occurrenceTypesTable.getSelectionModel().getSelectedItem();

        if (selectedOccurrenceType != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir o tipo de ocorrência " + selectedOccurrenceType.getName() + "?")) {
                try {
                    OccurrenceTypeManager.getInstance().delete(selectedOccurrenceType);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar um tipo de ocorrência");
        }
    }
}
