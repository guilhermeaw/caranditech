package controllers;

import db.managers.EmployeeManager;
import db.managers.OccupationManager;
import db.managers.WingManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Employee;
import models.Occupation;
import models.Wing;
import utils.ApplicationUtilities;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeesPaneController implements Initializable {
    @FXML
    private TableView<Employee> employeesTable;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> cpfColumn;

    @FXML
    private TableColumn<Employee, String> phoneColumn;

    @FXML
    private TableColumn<Employee, String> occupationColumn;

    @FXML
    private TableColumn<Employee, String> wingColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        employeesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            List<Employee> employees = EmployeeManager.getInstance().getAll();

            ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList(employees);

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            cpfColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getCpf()));
            phoneColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getPhone()));
            occupationColumn.setCellValueFactory(column -> {
                Occupation occupation = getOccupationById(column.getValue().getOccupationId());

                return new SimpleStringProperty(occupation != null ? occupation.getName() : "n/d");
            });
            wingColumn.setCellValueFactory(column -> {
                Wing wing = getWingById(column.getValue().getWingId());

                return new SimpleStringProperty(wing != null ? wing.getName() : "n/d");
            });

            employeesTable.setItems(employeeObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddEmployee() {}

    public void handleEditEmployee() {}

    public void handleDeleteEmployee() {}

    private Occupation getOccupationById(int id) {
        try {
            return OccupationManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }

    private Wing getWingById(int id) {
        try {
            return WingManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }
}
