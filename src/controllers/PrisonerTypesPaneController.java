package controllers;

import db.managers.PrisonerTypeManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.PrisonerType;
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

    public void handleAddPrisonerType() {}

    public void handleEditPrisonerType() {}

    public void handleDeletePrisonerType() {}
}
