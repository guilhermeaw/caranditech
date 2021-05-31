package controllers;

import common.EditorCallback;
import db.managers.PrisonerManager;
import db.managers.VisitManager;
import db.managers.VisitorManager;
import editors.VisitEditor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Prisoner;
import models.Visit;
import models.Visitor;
import services.AlertService;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VisitsPaneController implements Initializable {
    @FXML
    private TableView<Visit> visitsTable;

    @FXML
    private TableColumn<Visit, String> scheduleDateColumn;

    @FXML
    private TableColumn<Visit, String> prisonerColumn;

    @FXML
    private TableColumn<Visit, String> visitorColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        visitsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            List<Visit> visits = VisitManager.getInstance().getAll();

            ObservableList<Visit> visitObservableList = FXCollections.observableArrayList(visits);

            scheduleDateColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getScheduleDate().toString()));
            prisonerColumn.setCellValueFactory(column -> {
                Prisoner prisoner = getPrisonerById(column.getValue().getPrisonerId());

                return new SimpleStringProperty(prisoner != null ? prisoner.getName() : "n/d");
            });
            visitorColumn.setCellValueFactory(column -> {
                Visitor visitor = getVisitorById(column.getValue().getVisitorId());

                return new SimpleStringProperty(visitor != null ? visitor.getName() : "n/d");
            });

            visitsTable.setItems(visitObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddVisit() {
        new VisitEditor(new EditorCallback<Visit>(new Visit()) {
            @Override
            public void onEvent() {
                try {
                    VisitManager.getInstance().create((Visit) getSource());

                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    public void handleEditVisit() {
        Visit selectedVisit = visitsTable.getSelectionModel().getSelectedItem();

        if (selectedVisit != null) {
            new VisitEditor(new EditorCallback<Visit>(selectedVisit) {
                @Override
                public void onEvent() {
                    try {
                        VisitManager.getInstance().update((Visit) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar uma visita");
        }
    }

    public void handleDeleteVisit() {
        Visit selectedVisit = visitsTable.getSelectionModel().getSelectedItem();

        if (selectedVisit != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir a visita?")) {
                try {
                    VisitManager.getInstance().delete(selectedVisit);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar uma visita");
        }
    }

    private Prisoner getPrisonerById(int id) {
        try {
            return PrisonerManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }

    private Visitor getVisitorById(int id) {
        try {
            return VisitorManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }
}
