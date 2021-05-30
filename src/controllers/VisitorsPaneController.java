package controllers;

import db.managers.VisitorManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Visitor;
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
            cpfColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getCpf()));
            phoneColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getPhone()));

            visitorsTable.setItems(visitorObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddVisitor() {}

    public void handleEditVisitor() {}

    public void handleDeleteVisitor() {}
}
