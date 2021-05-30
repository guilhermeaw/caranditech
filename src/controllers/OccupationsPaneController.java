package controllers;

import common.EditorCallback;
import editors.OccupationEditor;
import common.Tooltip;
import db.managers.OccupationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Occupation;
import services.AlertService;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OccupationsPaneController implements Initializable {
    @FXML
    private TableView<Occupation> occupationsTable;

    @FXML
    private TableColumn<Occupation, String> nameColumn;

    @FXML
    private TableColumn<Occupation, String> infoColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        occupationsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            List<Occupation> occupations = OccupationManager.getInstance().getAll();

            ObservableList<Occupation> occupationObservableList = FXCollections.observableArrayList(occupations);

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            infoColumn.setCellFactory(column -> new TableCell<Occupation, String>() {
                @Override
                protected void updateItem(String s, boolean b) {
                super.updateItem(s, b);
                setText(s);

                Tooltip tooltip = new Tooltip(s);
                tooltip.setMultiline(true);
                setTooltip(tooltip);
                }
            });

            occupationsTable.setItems(occupationObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddOccupation() {
        new OccupationEditor(new EditorCallback<Occupation>(new Occupation()) {
            @Override
            public void onEvent() {
                try {
                    OccupationManager.getInstance().create((Occupation) getSource());

                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    public void handleEditOccupation() {
        Occupation selectedOccupation = occupationsTable.getSelectionModel().getSelectedItem();

        if (selectedOccupation != null) {
            new OccupationEditor(new EditorCallback<Occupation>(selectedOccupation) {
                @Override
                public void onEvent() {
                    try {
                        OccupationManager.getInstance().update((Occupation) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar um cargo");
        }
    }

    public void handleDeleteOccupation() {
        Occupation selectedOccupation = occupationsTable.getSelectionModel().getSelectedItem();

        if (selectedOccupation != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir o cargo " + selectedOccupation.getName() + "?")) {
                try {
                    OccupationManager.getInstance().delete(selectedOccupation);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar um cargo");
        }
    }
}
