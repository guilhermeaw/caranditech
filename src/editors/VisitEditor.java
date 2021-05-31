package editors;

import common.DefaultEditor;
import common.EditorCallback;
import db.managers.PrisonerManager;
import db.managers.VisitorManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.*;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;

public class VisitEditor extends DefaultEditor<Visit> {
    public VisitEditor(EditorCallback<Visit> callback) {
        super(callback);

        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        if (dpScheduleDate.getValue() == null) {
            errors.add("É necessário informar uma data");
        }

        if (cbPrisoner.getValue() == null) {
            errors.add("É necessário informar um prisioneiro");
        }

        if (cbVisitor.getValue() == null) {
            errors.add("É necessário informar um visitante");
        }
    }

    @Override
    protected void obtainInput() {
        source.setScheduleDate(DateUtils.getDateByLocalDate(dpScheduleDate.getValue()));
        source.setPrisonerId(cbPrisoner.getValue().getId());
        source.setVisitorId(cbVisitor.getValue().getId());
    }

    @Override
    protected void setSource(Visit source) {
        if (source.getId() != 0) {
            dpScheduleDate.setValue(DateUtils.getLocalDateByDate(source.getScheduleDate()));
            cbPrisoner.setValue(getPrisonerById(source.getPrisonerId()));
            cbVisitor.setValue(getVisitorById(source.getVisitorId()));
        }
    }

    private Prisoner getPrisonerById(int id) {
        try {
            return PrisonerManager.getInstance().getById(id);
        } catch (Exception e) {
            handleException(e);
        }

        return null;
    }

    private Visitor getVisitorById(int id) {
        try {
            return VisitorManager.getInstance().getById(id);
        } catch (Exception e) {
            handleException(e);
        }

        return null;
    }

    private void loadComboItems() {
        try {
            List<Prisoner> prisoners = PrisonerManager.getInstance().getAll();
            List<Visitor> visitors = VisitorManager.getInstance().getAll();

            cbPrisoner.setItems(FXCollections.observableArrayList(prisoners));
            cbVisitor.setItems(FXCollections.observableArrayList(visitors));
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void initComponents() {
        setTitle("Editor de Visita");
        setHeaderText("Editor de Visita");

        cbPrisoner.setPrefWidth(300);
        cbVisitor.setPrefWidth(300);

        setSource(source);
        loadComboItems();

        grid.setPadding(new Insets(500));
        grid.setStyle("-fx-padding: 30;");
        grid.setPrefWidth(500);
        grid.setVgap(15);
        grid.setHgap(15);

        grid.add(lbScheduleDate, 0, 0, 1, 1);
        grid.add(dpScheduleDate, 1, 0, 1, 1);

        grid.add(lbPrisoner, 0, 1, 1, 1);
        grid.add(cbPrisoner, 1, 1, 1, 1);

        grid.add(lbVisitor, 0, 2, 1, 1);
        grid.add(cbVisitor, 1, 2, 1, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent(grid);
    }

    private GridPane grid = new GridPane();

    private Label lbScheduleDate = new Label("Data: *");
    private DatePicker dpScheduleDate = new DatePicker(LocalDate.now());

    private Label lbPrisoner = new Label("Prisioneiro: *");
    private ComboBox<Prisoner> cbPrisoner = new ComboBox();

    private Label lbVisitor = new Label("Visitante: *");
    private ComboBox<Visitor> cbVisitor = new ComboBox();
}
