package controllers;

import common.EditorCallback;
import common.WingEditor;
import db.managers.WingManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Wing;
import services.AlertService;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WingsPaneController implements Initializable {
    @FXML
    private TableView<Wing> wingsTable;

    @FXML
    private TableColumn<Wing, String> nameColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        wingsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            List<Wing> wings = WingManager.getInstance().getAll();

            ObservableList<Wing> wingObservableList = FXCollections.observableArrayList(wings);

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));

            wingsTable.setItems(wingObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddWing() {
        new WingEditor(new EditorCallback<Wing>(new Wing()) {
            @Override
            public void onEvent() {
                try {
                    WingManager.getInstance().create((Wing) getSource());

                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    public void handleEditWing() {
        Wing selectedWing = wingsTable.getSelectionModel().getSelectedItem();

        if (selectedWing != null) {
            new WingEditor(new EditorCallback<Wing>(selectedWing) {
                @Override
                public void onEvent() {
                    try {
                        WingManager.getInstance().update((Wing) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar uma Ala");
        }
    }

    public void handleDeleteWing() {
        Wing selectedWing = wingsTable.getSelectionModel().getSelectedItem();

        if (selectedWing != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir a ala " + selectedWing.getName())) {
                try {
                    WingManager.getInstance().delete(selectedWing);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar uma Ala");
        }
    }
}
