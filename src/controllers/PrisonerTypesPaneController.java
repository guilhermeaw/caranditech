package controllers;

import common.EditorCallback;
import db.managers.PrisonerTypeManager;
import db.managers.PrisonerTypeManager;
import db.managers.PrisonerTypeManager;
import editors.PrisonerTypeEditor;
import editors.PrisonerTypeEditor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.PrisonerType;
import models.PrisonerType;
import models.PrisonerType;
import services.AlertService;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PrisonerTypesPaneController implements Initializable {
    @FXML
    private TableView<PrisonerType> prisonerTypesTable;

    @FXML
    private TableColumn<PrisonerType, String> nameColumn;

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
        try {
            List<PrisonerType> prisonerTypes = PrisonerTypeManager.getInstance().getAll();

            ObservableList<PrisonerType> prisonerTypeObservableList = FXCollections.observableArrayList(prisonerTypes);

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            profitColumn.setCellValueFactory(column -> new SimpleStringProperty(Double.toString(column.getValue().getProfit())));
            infoColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getDescription()));

            prisonerTypesTable.setItems(prisonerTypeObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddPrisonerType() {
        new PrisonerTypeEditor(new EditorCallback<PrisonerType>(new PrisonerType()) {
            @Override
            public void onEvent() {
                try {
                    PrisonerTypeManager.getInstance().create((PrisonerType) getSource());

                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    public void handleEditPrisonerType() {
        PrisonerType selectedPrisonerType = prisonerTypesTable.getSelectionModel().getSelectedItem();

        if (selectedPrisonerType != null) {
            new PrisonerTypeEditor(new EditorCallback<PrisonerType>(selectedPrisonerType) {
                @Override
                public void onEvent() {
                    try {
                        PrisonerTypeManager.getInstance().update((PrisonerType) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar um tipo de prisioneiro");
        }
    }

    public void handleDeletePrisonerType() {
        PrisonerType selectedPrisonerType = prisonerTypesTable.getSelectionModel().getSelectedItem();

        if (selectedPrisonerType != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir o tipo de prisioneiro " + selectedPrisonerType.getName())) {
                try {
                    PrisonerTypeManager.getInstance().delete(selectedPrisonerType);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar um tipo de prisioneiro");
        }
    }
}
