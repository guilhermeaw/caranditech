package controllers;

import common.EditorCallback;
import common.Tooltip;
import db.managers.CellManager;
import db.managers.PrisonerManager;
import db.managers.PrisonerTypeManager;
import editors.PrisonerEditor;
import formatters.DateFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Cell;
import models.Prisoner;
import models.PrisonerType;
import reports.PrisonerReport;
import reports.PrisonersListReport;
import services.AlertService;
import utils.ApplicationUtilities;
import utils.FileUtilities;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PrisonersPaneController implements Initializable {
    @FXML
    private TableView<Prisoner> prisonersTable;

    @FXML
    private TableColumn<Prisoner, String> nameColumn;

    @FXML
    private TableColumn<Prisoner, String> prisonerTypeColumn;

    @FXML
    private TableColumn<Prisoner, String> cellColumn;

    @FXML
    private TableColumn<Prisoner, String> enterDateColumn;

    @FXML
    private TableColumn<Prisoner, String> exitDateColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button printButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        printButton.setTooltip(new Tooltip("Imprimir relatório de prisioneiros"));
        refreshContent();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        prisonersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            List<Prisoner> prisoners = PrisonerManager.getInstance().getAll();

            ObservableList<Prisoner> prisonerObservableList = FXCollections.observableArrayList(prisoners);

            nameColumn.setCellValueFactory(column -> new SimpleStringProperty(column.getValue().getName()));
            prisonerTypeColumn.setCellValueFactory(column -> {
                PrisonerType prisonerType = getPrisonerTypeById(column.getValue().getPrisonerTypeId());

                return new SimpleStringProperty(prisonerType != null ? prisonerType.getName() : "n/d");
            });
            cellColumn.setCellValueFactory(column -> {
                Cell cell = getCellById(column.getValue().getCellId());

                return new SimpleStringProperty(cell != null ? cell.getName() : "n/d");
            });
            enterDateColumn.setCellValueFactory(column -> new SimpleStringProperty(DateFormatter.format(column.getValue().getEnterDate())));
            exitDateColumn.setCellValueFactory(column -> {
                Date exitDate = column.getValue().getExitDate();

                return new SimpleStringProperty(exitDate != null ? DateFormatter.format(exitDate) : "n/d");
            });

            prisonersTable.setItems(prisonerObservableList);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleAddPrisoner() {
        new PrisonerEditor(new EditorCallback<Prisoner>(new Prisoner()) {
            @Override
            public void onEvent() {
                try {
                    PrisonerManager.getInstance().create((Prisoner) getSource());

                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
    }

    public void handleEditPrisoner() {
        Prisoner selectedPrisoner = prisonersTable.getSelectionModel().getSelectedItem();

        if (selectedPrisoner != null) {
            new PrisonerEditor(new EditorCallback<Prisoner>(selectedPrisoner) {
                @Override
                public void onEvent() {
                    try {
                        PrisonerManager.getInstance().update((Prisoner) getSource());

                        refreshContent();
                    } catch ( Exception e ) {
                        ApplicationUtilities.getInstance().handleException(e);
                    }
                }
            } ).open();
        } else {
            AlertService.showWarning("É necessário selecionar um prisioneiro");
        }
    }

    public void handleDeletePrisoner() {
        Prisoner selectedPrisoner = prisonersTable.getSelectionModel().getSelectedItem();

        if (selectedPrisoner != null) {
            if (AlertService.showConfirmation("Tem certeza que deseja excluir o prisioneiro " + selectedPrisoner.getName() + "?")) {
                try {
                    PrisonerManager.getInstance().delete(selectedPrisoner);

                    refreshContent();
                } catch (Exception e) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } else {
            AlertService.showWarning("É necessário selecionar um prisioneiro");
        }
    }

    public void handlePrint() {
        try {
            Prisoner selectedPrisoner = prisonersTable.getSelectionModel().getSelectedItem();

            if (selectedPrisoner != null) {
                File file = FileUtilities.saveFile( "Imprimir Relatório", "PrisonerReport-" + System.currentTimeMillis() +".pdf" );

                if (file != null) {
                    PrisonerReport report = new PrisonerReport(selectedPrisoner);
                    report.generatePDF(file);
                }
            } else {
                File file = FileUtilities.saveFile( "Imprimir Relatório", "PrisonersListReport-" + System.currentTimeMillis() +".pdf" );

                if (file != null) {
                    PrisonersListReport report = new PrisonersListReport(PrisonerManager.getInstance().getAll());
                    report.generatePDF(file);
                }
            }
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    private PrisonerType getPrisonerTypeById(int id) {
        try {
            return PrisonerTypeManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }

    private Cell getCellById(int id) {
        try {
            return CellManager.getInstance().getById(id);
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return null;
    }
}
