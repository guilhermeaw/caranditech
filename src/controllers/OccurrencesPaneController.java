package controllers;

import common.EditorCallback;
import db.managers.OccurrenceManager;
import db.managers.PrisonerManager;
import db.managers.UserManager;
import editors.OccurrenceEditor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Occurrence;
import models.Prisoner;
import models.User;
import services.AlertService;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OccurrencesPaneController implements Initializable {
    @FXML
    private TableView<Occurrence> occurrencesTable;

    @FXML
    private TableColumn<Occurrence, String> titleColumn;

    @FXML
    private TableColumn<Occurrence, String> prisonerColumn;

    @FXML
    private TableColumn<Occurrence, String> authorColumn;

    @FXML
    private TableColumn<Occurrence, String> createdDateColumn;

    @FXML
    private TableColumn<Occurrence, String> infoColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        occurrencesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            List<Occurrence> occurrences = OccurrenceManager.getInstance().getAll();

            ObservableList<Occurrence> occurrenceObservableList = FXCollections.observableArrayList(occurrences);

            titleColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getTitle()));
            prisonerColumn.setCellValueFactory(column -> {
                Prisoner prisoner = getPrisonerById(column.getValue().getPrisonerId());

                return new SimpleStringProperty(prisoner != null ? prisoner.getName() : "n/d");
            });
            authorColumn.setCellValueFactory(column -> {
                User user = getUserById(column.getValue().getAuthorId());

                return new SimpleStringProperty(user != null ? user.getName() : "n/d");
            });
            createdDateColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getCreatedDate().toString()));
            infoColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getDescription()));

            occurrencesTable.setItems(occurrenceObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddOccurrence() {
        new OccurrenceEditor(new EditorCallback<Occurrence>(new Occurrence()) {
            @Override
            public void onEvent() {
                try {
                    OccurrenceManager.getInstance().create((Occurrence) getSource());

                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    public void handleEditOccurrence() {
        Occurrence selectedOccurrence = occurrencesTable.getSelectionModel().getSelectedItem();

        if (selectedOccurrence != null) {
            new OccurrenceEditor(new EditorCallback<Occurrence>(selectedOccurrence) {
                @Override
                public void onEvent() {
                    try {
                        OccurrenceManager.getInstance().update((Occurrence) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar uma ocorrência");
        }
    }

    public void handleDeleteOccurrence() {
        Occurrence selectedOccurrence = occurrencesTable.getSelectionModel().getSelectedItem();

        if (selectedOccurrence != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir o prisioneiro " + selectedOccurrence.getTitle() + "?")) {
                try {
                    OccurrenceManager.getInstance().delete(selectedOccurrence);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar uma ocorrência");
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

    private User getUserById(int id) {
        try {
            return UserManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }
}
