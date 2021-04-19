package controllers;

import db.managers.WingManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Wing;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WingsCellsPaneController implements Initializable {
    @FXML
    private TableView<Wing> wingsTable;

    @FXML
    private TableColumn<Wing, String> nameColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();
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
}
