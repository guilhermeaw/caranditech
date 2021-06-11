package filters.editors;

import common.DefaultEditor;
import common.EditorCallback;
import common.Identity;
import db.managers.OccurrenceTypeManager;
import db.managers.PrisonerManager;
import db.managers.UserManager;
import filters.data.OccurrenceFilter;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.OccurrenceType;
import models.Prisoner;
import models.User;
import utils.DateUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OccurrenceFilterEditor extends DefaultEditor<OccurrenceFilter> {
    public OccurrenceFilterEditor(EditorCallback<OccurrenceFilter> callback) {
        super(callback);
        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        LocalDate startDateRange = dpStartCreatedDate.getValue();
        LocalDate endDateRange = dpEndCreatedDate.getValue();

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
        LocalDate startDate = dpStartCreatedDate.getValue();
        LocalDate endDate = dpEndCreatedDate.getValue();
        String state = cbState.getValue();

        source.setTitle(tfTitle.getText());
        source.setOccurrenceType(cbOccurrenceType.getValue());
        source.setPrisoner(cbPrisoner.getValue());
        source.setAuthor(cbAuthor.getValue());

        if (startDate != null) {
            source.setStartDateRange(DateUtils.getDateByLocalDate(dpStartCreatedDate.getValue()));
        }

        if (endDate != null) {
            source.setEndDateRange(DateUtils.getDateByLocalDate(dpEndCreatedDate.getValue()));
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
    protected void setSource(OccurrenceFilter source) {
        Date startDate = source.getStartDateRange();
        Date endDate = source.getEndDateRange();

        tfTitle.setText(source.getTitle());
        cbOccurrenceType.setValue(source.getOccurrenceType());
        cbPrisoner.setValue(source.getPrisoner());
        cbAuthor.setValue(source.getAuthor());

        if (startDate != null) {
            dpStartCreatedDate.setValue(DateUtils.getLocalDateByDate(startDate));
        }

        if (endDate != null) {
            dpEndCreatedDate.setValue(DateUtils.getLocalDateByDate(endDate));
        }

        cbState.setValue(Identity.STATES_MAP.get(source.getState()));
    }

    @Override
    protected void onClear() {
        setSource(new OccurrenceFilter());
    }

    private void loadComboItems() {
        try {
            List<Prisoner> prisoners = PrisonerManager.getInstance().getAll();
            List<User> users = UserManager.getInstance().getAll();
            List<OccurrenceType> occurrenceTypes = OccurrenceTypeManager.getInstance().getAll();

            cbPrisoner.setItems(FXCollections.observableArrayList(prisoners));
            cbAuthor.setItems(FXCollections.observableArrayList(users));
            cbOccurrenceType.setItems(FXCollections.observableArrayList(occurrenceTypes));
            cbState.setItems(FXCollections.observableArrayList(Identity.STATES_MAP.values()));
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void initComponents() {
        setTitle("Filtro de Ocorrência");
        setHeaderText("Filtro de Ocorrência");

        cbPrisoner.setPrefWidth(200);
        cbAuthor.setPrefWidth(200);
        cbState.setPrefWidth(200);
        cbOccurrenceType.setPrefWidth(200);

        dpStartCreatedDate.setPrefWidth(150);
        dpEndCreatedDate.setPrefWidth(150);

        setSource(source);
        activeClearButton();
        loadComboItems();

        grid.setPadding(new Insets(580));
        grid.setStyle("-fx-padding: 30;");
        grid.setPrefWidth(580);
        grid.setVgap(15);
        grid.setHgap(15);

        grid.add(lbTitle, 0, 0, 1, 1);
        grid.add(tfTitle, 1, 0, 1, 1);

        grid.add(lbOccurrenceType, 0, 1, 1, 1);
        grid.add(cbOccurrenceType, 1, 1, 1, 1);

        grid.add(lbPrisoner, 0, 2, 1, 1);
        grid.add(cbPrisoner, 1, 2, 1, 1);

        grid.add(lbAuthor, 0, 3, 1, 1);
        grid.add(cbAuthor, 1, 3, 1, 1);

        grid.add(lbCreatedDateRange, 0, 4, 1, 1);
        grid.add(dpStartCreatedDate, 1, 4, 1, 1);
        grid.add(lbRangeTo, 2, 4, 1, 1);
        grid.add(dpEndCreatedDate, 3, 4, 1, 1);

        grid.add(lbState, 0, 5, 1, 1);
        grid.add(cbState, 1, 5, 1, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent(grid);
    }

    private GridPane grid = new GridPane();

    private Label lbTitle = new Label("Título:");
    private TextField tfTitle = new TextField();

    private Label lbOccurrenceType = new Label("Tipo de Ocorrência:");
    private ComboBox<OccurrenceType> cbOccurrenceType = new ComboBox();

    private Label lbPrisoner = new Label("Prisioneiro:");
    private ComboBox<Prisoner> cbPrisoner = new ComboBox();

    private Label lbAuthor = new Label("Autor:");
    private ComboBox<User> cbAuthor = new ComboBox();

    private Label lbCreatedDateRange = new Label("Data de criação:");
    private DatePicker dpStartCreatedDate = new DatePicker();
    private Label lbRangeTo = new Label("até");
    private DatePicker dpEndCreatedDate = new DatePicker();

    private Label lbState = new Label("Status:");
    private ComboBox<String> cbState = new ComboBox();
}
