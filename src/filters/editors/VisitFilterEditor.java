package filters.editors;

import common.DefaultEditor;
import common.EditorCallback;
import common.Identity;
import db.managers.PrisonerManager;
import db.managers.VisitorManager;
import filters.data.VisitFilter;
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

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class VisitFilterEditor extends DefaultEditor<VisitFilter> {
    public VisitFilterEditor(EditorCallback<VisitFilter> callback) {
        super(callback);
        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        LocalDate startDateRange = dpStartScheduleDate.getValue();
        LocalDate endDateRange = dpEndScheduleDate.getValue();

        boolean someDateIsFilled = startDateRange != null || endDateRange != null;
        boolean someDateIsNotFilled = startDateRange == null || endDateRange == null;

        boolean allDatesFilled = startDateRange != null && endDateRange != null;

        if (someDateIsFilled && someDateIsNotFilled) {
            errors.add("É necessário informar a data de início e de fim");
        }

        if (allDatesFilled && endDateRange.isBefore(startDateRange)) {
            errors.add("A data final deve ser posterior à data inicial");
        }
    }

    @Override
    protected void obtainInput() {
        LocalDate startDate = dpStartScheduleDate.getValue();
        LocalDate endDate = dpEndScheduleDate.getValue();
        String state = cbState.getValue();

        source.setPrisoner(cbPrisoner.getValue());
        source.setVisitor(cbVisitor.getValue());

        if (startDate != null) {
            source.setStartScheduleDateRange(DateUtils.getDateByLocalDate(dpStartScheduleDate.getValue()));
        }

        if (endDate != null) {
            source.setEndScheduleDateRange(DateUtils.getDateByLocalDate(dpEndScheduleDate.getValue()));
        }

        if (state != null) {
            source.setState(Identity.STATES_MAP.entrySet()
                    .stream()
                    .filter(entry -> cbState.getValue().equals(entry.getValue()))
                    .map(Map.Entry::getKey)
                    .findFirst().get());
        }
    }

    @Override
    protected void setSource(VisitFilter source) {
        Date startDate = source.getStartScheduleDateRange();
        Date endDate = source.getEndScheduleDateRange();

        cbPrisoner.setValue(source.getPrisoner());
        cbVisitor.setValue(source.getVisitor());
        dpStartScheduleDate.setValue(startDate != null ? DateUtils.getLocalDateByDate(startDate) : null);
        dpEndScheduleDate.setValue(endDate != null ? DateUtils.getLocalDateByDate(endDate) : null);
        cbState.setValue(Identity.STATES_MAP.get(source.getState()));
    }

    @Override
    protected void onClear() {
        setSource(new VisitFilter());
    }

    private void loadComboItems() {
        try {
            List<Prisoner> prisoners = PrisonerManager.getInstance().getAll();
            List<Visitor> visitors = VisitorManager.getInstance().getAll();

            cbPrisoner.setItems(FXCollections.observableArrayList(prisoners));
            cbVisitor.setItems(FXCollections.observableArrayList(visitors));
            cbState.setItems(FXCollections.observableArrayList(Identity.STATES_MAP.values()));
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void initComponents() {
        setTitle("Filtro de Visitas");
        setHeaderText("Filtro de Visitas");

        cbPrisoner.setPrefWidth(200);
        cbVisitor.setPrefWidth(200);
        cbState.setPrefWidth(200);

        dpStartScheduleDate.setPrefWidth(150);
        dpEndScheduleDate.setPrefWidth(150);

        setSource(source);
        activeClearButton();
        loadComboItems();

        grid.setPadding(new Insets(620));
        grid.setStyle("-fx-padding: 30;");
        grid.setPrefWidth(620);
        grid.setVgap(15);
        grid.setHgap(15);

        grid.add(lbPrisoner, 0, 0, 1, 1);
        grid.add(cbPrisoner, 1, 0, 1, 1);

        grid.add(lbVisitor, 0, 1, 1, 1);
        grid.add(cbVisitor, 1, 1, 1, 1);

        grid.add(lbScheduleDateRange, 0, 2, 1, 1);
        grid.add(dpStartScheduleDate, 1, 2, 1, 1);
        grid.add(lbRangeTo, 2, 2, 1, 1);
        grid.add(dpEndScheduleDate, 3, 2, 1, 1);

        grid.add(lbState, 0, 3, 1, 1);
        grid.add(cbState, 1, 3, 1, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent(grid);
    }

    private GridPane grid = new GridPane();

    private Label lbPrisoner = new Label("Prisioneiro:");
    private ComboBox<Prisoner> cbPrisoner = new ComboBox();

    private Label lbVisitor = new Label("Visitante:");
    private ComboBox<Visitor> cbVisitor = new ComboBox();

    private Label lbScheduleDateRange = new Label("Data de agendamento:");
    private DatePicker dpStartScheduleDate = new DatePicker();
    private Label lbRangeTo = new Label("até");
    private DatePicker dpEndScheduleDate = new DatePicker();

    private Label lbState = new Label("Status:");
    private ComboBox<String> cbState = new ComboBox();
}
