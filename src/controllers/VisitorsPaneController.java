package controllers;

import common.EditorCallback;
import db.managers.VisitorManager;
import editors.VisitorEditor;
import formatters.CpfFormatter;
import formatters.PhoneFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Visitor;
import services.AlertService;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VisitorsPaneController implements Initializable {
    @FXML
    private TableView<Visitor> visitorsTable;

    @FXML
    private TableColumn<Visitor, String> nameColumn;

    @FXML
    private TableColumn<Visitor, String> cpfColumn;

    @FXML
    private TableColumn<Visitor, String> phoneColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        visitorsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            List<Visitor> visitors = VisitorManager.getInstance().getAll();

            ObservableList<Visitor> visitorObservableList = FXCollections.observableArrayList(visitors);

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            cpfColumn.setCellValueFactory(column -> new SimpleStringProperty(CpfFormatter.format(column.getValue().getCpf())));
            phoneColumn.setCellValueFactory(column -> new SimpleStringProperty(PhoneFormatter.format(column.getValue().getPhone())));

            visitorsTable.setItems(visitorObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddVisitor() {
        new VisitorEditor(new EditorCallback<Visitor>(new Visitor()) {
            @Override
            public void onEvent() {
                try {
                    VisitorManager.getInstance().create((Visitor) getSource());

                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    public void handleEditVisitor() {
        Visitor selectedVisitor = visitorsTable.getSelectionModel().getSelectedItem();

        if (selectedVisitor != null) {
            new VisitorEditor(new EditorCallback<Visitor>(selectedVisitor) {
                @Override
                public void onEvent() {
                    try {
                        VisitorManager.getInstance().update((Visitor) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar um visitante");
        }
    }

    public void handleDeleteVisitor() {
        Visitor selectedVisitor = visitorsTable.getSelectionModel().getSelectedItem();

        if (selectedVisitor != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir o visitante " + selectedVisitor.getName() + "?")) {
                try {
                    VisitorManager.getInstance().delete(selectedVisitor);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar um visitante");
        }
    }
}
