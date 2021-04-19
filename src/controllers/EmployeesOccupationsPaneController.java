package controllers;

import common.Tooltip;
import db.managers.OccupationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Occupation;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeesOccupationsPaneController implements Initializable {
    @FXML
    private TableView<Occupation> occupationsTable;

    @FXML
    private TableColumn<Occupation, String> nameColumn;

    @FXML
    private TableColumn<Occupation, String> infoColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();
    }

    public void refreshContent() {
        try {
            List<Occupation> occupations = OccupationManager.getInstance().getAll();

            ObservableList<Occupation> occupationObservableList = FXCollections.observableArrayList(occupations);

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            infoColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getDescription()));

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
}
