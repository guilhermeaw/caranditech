package controllers;

import common.EditorCallback;
import common.Tooltip;
import db.managers.PrisonerManager;
import db.managers.VisitManager;
import db.managers.VisitorManager;
import editors.VisitEditor;
import filters.data.VisitFilter;
import filters.editors.VisitFilterEditor;
import formatters.DateFormatter;
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
import reports.VisitListReport;
import reports.VisitReport;
import services.AlertService;
import utils.ApplicationUtilities;
import utils.FileUtilities;

import java.io.File;
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

    @FXML
    private Button filterButton;

    @FXML
    private Button printButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTooltips();
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
            List<Visit> visits = VisitManager.getInstance().getByFilter(filter);

            ObservableList<Visit> visitObservableList = FXCollections.observableArrayList(visits);

            scheduleDateColumn.setCellValueFactory(column -> new SimpleStringProperty(DateFormatter.format(column.getValue().getScheduleDate())));
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
                    boolean hasVisit = VisitManager.getInstance().hasVisit((Visit) getSource());

                    if (hasVisit) {
                        AlertService.showWarning("J?? existe uma visita para esse prisioneiro na data selecionada");
                        return;
                    }

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
            AlertService.showWarning("?? necess??rio selecionar uma visita");
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
            AlertService.showWarning("?? necess??rio selecionar uma visita");
        }
    }

    public void handlePrint() {
        try {
            Visit selectedVisit = visitsTable.getSelectionModel().getSelectedItem();

            if (selectedVisit != null) {
                File file = FileUtilities.saveFile( "Imprimir Relat??rio", "VisitReport-" + System.currentTimeMillis() +".pdf" );

                if (file != null) {
                    VisitReport report = new VisitReport(selectedVisit);
                    report.generatePDF(file);
                }
            } else {
                File file = FileUtilities.saveFile( "Imprimir Relat??rio", "VisitListReport-" + System.currentTimeMillis() +".pdf" );

                if (file != null) {
                    VisitListReport report = new VisitListReport(VisitManager.getInstance().getByFilter(filter));
                    report.generatePDF(file);
                }
            }
        } catch (Exception e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    public void handleOpenFilter() {
        new VisitFilterEditor(new EditorCallback<VisitFilter>(filter) {
            @Override
            public void onEvent() {
                try {
                    refreshContent();
                } catch ( Exception e ) {
                    ApplicationUtilities.getInstance().handleException(e);
                }
            }
        } ).open();
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

    private void loadTooltips() {
        printButton.setTooltip(new Tooltip("Imprimir relat??rio de visitas"));
        filterButton.setTooltip(new Tooltip("Filtrar visitas"));
    }

    private VisitFilter filter = new VisitFilter();
}
