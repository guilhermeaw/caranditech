package editors;

import common.DefaultEditor;
import common.EditorCallback;
import db.managers.CellManager;
import db.managers.PrisonerTypeManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.Cell;
import models.Prisoner;
import models.PrisonerType;
import utils.DateUtils;
import validators.EmptyValidator;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

public class PrisonerEditor extends DefaultEditor<Prisoner> {
    public PrisonerEditor(EditorCallback<Prisoner> callback) {
        super(callback);

        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        if (!EmptyValidator.validate(tfName.getText())) {
            errors.add("É necessário informar um nome");
        }

        if (cbPrisonerType.getValue() == null) {
            errors.add("É necessário informar um tipo de prisioneiro");
        }

        if (cbCell.getValue() == null) {
            errors.add("É necessário informar uma cela");
        }

        if (dpEnterDate.getValue() == null) {
            errors.add("É necessário informar uma data de entrada");
        }
    }

    @Override
    protected void obtainInput() {
        LocalDate exitDate = dpExitDate.getValue();

        source.setName(tfName.getText());
        source.setPrisonerTypeId(cbPrisonerType.getValue().getId());
        source.setCellId(cbCell.getValue().getId());
        source.setEnterDate(DateUtils.getDateByLocalDate(dpEnterDate.getValue()));
        source.setExitDate(exitDate != null
                ? DateUtils.getDateByLocalDate(dpExitDate.getValue())
                : null);
    }

    @Override
    protected void setSource(Prisoner source) {
        if (source.getId() != 0) {
            Date exitDate = source.getExitDate();

            tfName.setText(source.getName());
            cbPrisonerType.setValue(getPrisonerTypeById(source.getPrisonerTypeId()));
            cbCell.setValue(getCellById(source.getCellId()));
            dpEnterDate.setValue(DateUtils.getLocalDateByDate(source.getEnterDate()));
            dpExitDate.setValue(exitDate != null ? DateUtils.getLocalDateByDate(exitDate) : null);
        } else {
            dpExitDate.setDisable(true);
        }
    }

    private PrisonerType getPrisonerTypeById(int id) {
        try {
            return PrisonerTypeManager.getInstance().getById(id);
        } catch (Exception e) {
            handleException(e);
        }

        return null;
    }

    private Cell getCellById(int id) {
        try {
            return CellManager.getInstance().getById(id);
        } catch (Exception e) {
            handleException(e);
        }

        return null;
    }

    private void loadComboItems() {
        try {
            List<PrisonerType> prisonerTypes = PrisonerTypeManager.getInstance().getAll();
            List<Cell> cells = CellManager.getInstance().getAll();

            cbPrisonerType.setItems(FXCollections.observableArrayList(prisonerTypes));
            cbCell.setItems(FXCollections.observableArrayList(cells));
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void initComponents() {
        setTitle("Editor de Prisioneiro");
        setHeaderText("Editor de Prisioneiro");

        cbCell.setPrefWidth(300);
        cbPrisonerType.setPrefWidth(300);

        setSource(source);
        loadComboItems();

        grid.setPadding(new Insets(500));
        grid.setStyle("-fx-padding: 30;");
        grid.setPrefWidth(500);
        grid.setVgap(15);
        grid.setHgap(15);

        grid.add(lbName, 0, 0, 1, 1);
        grid.add(tfName, 1, 0, 1, 1);

        grid.add(lbPrisonerType, 0, 1, 1, 1);
        grid.add(cbPrisonerType, 1, 1, 1, 1);

        grid.add(lbCell, 0, 2, 1, 1);
        grid.add(cbCell, 1, 2, 1, 1);

        grid.add(lbEnterDate, 0, 3, 1, 1);
        grid.add(dpEnterDate, 1, 3, 1, 1);

        grid.add(lbExitDate, 0, 4, 1, 1);
        grid.add(dpExitDate, 1, 4, 1, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent(grid);
    }

    private GridPane grid = new GridPane();

    private Label lbName = new Label("Nome: *");
    private TextField tfName = new TextField();

    private Label lbPrisonerType = new Label("Tipo: *");
    private ComboBox<PrisonerType> cbPrisonerType = new ComboBox();

    private Label lbCell = new Label("Cela: *");
    private ComboBox<Cell> cbCell = new ComboBox();

    private Label lbEnterDate = new Label("Data de Entrada: *");
    private DatePicker dpEnterDate = new DatePicker(LocalDate.now());

    private Label lbExitDate = new Label("Data de Saída:");
    private DatePicker dpExitDate = new DatePicker();
}
