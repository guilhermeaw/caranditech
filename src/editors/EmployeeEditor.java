package editors;

import common.DefaultEditor;
import common.EditorCallback;
import common.MaskedTextField;
import db.managers.OccupationManager;
import db.managers.WingManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.Employee;
import models.Occupation;
import models.Wing;
import validators.CpfValidator;
import validators.EmptyValidator;

import java.util.List;

public class EmployeeEditor extends DefaultEditor<Employee> {
    public EmployeeEditor(EditorCallback<Employee> callback) {
        super(callback);

        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        if (!EmptyValidator.validate(tfName.getText())) {
            errors.add("É necessário informar um nome");
        }

        if (!CpfValidator.validate(tfCpf.getPlainText())) {
            errors.add("É necessário informar um CPF válido");
        }

        if (!EmptyValidator.validate(tfPhone.getPlainText())) {
            errors.add("É necessário informar um telefone válido");
        }

        if (cbOccupation.getValue() == null) {
            errors.add("É necessário informar um cargo");
        }

        if (cbWing.getValue() == null) {
            errors.add("É necessário informar uma ala");
        }
    }

    @Override
    protected void obtainInput() {
        source.setName(tfName.getText());
        source.setCpf(tfCpf.getPlainText());
        source.setPhone(tfPhone.getPlainText());
        source.setOccupationId(cbOccupation.getValue().getId());
        source.setWingId(cbWing.getValue().getId());
    }

    @Override
    protected void setSource(Employee source) {
        if (source.getId() != 0) {
            tfName.setText(source.getName());
            tfCpf.setPlainText(source.getCpf());
            tfPhone.setPlainText(source.getPhone());
            cbOccupation.setValue(getOccupationById(source.getOccupationId()));
            cbWing.setValue(getWingById(source.getWingId()));
        }
    }

    private Occupation getOccupationById(int id) {
        try {
            return OccupationManager.getInstance().getById(id);
        } catch (Exception e) {
            handleException(e);
        }

        return null;
    }

    private Wing getWingById(int id) {
        try {
            return WingManager.getInstance().getById(id);
        } catch (Exception e) {
            handleException(e);
        }

        return null;
    }

    private void loadComboItems() {
        try {
            List<Occupation> occupations = OccupationManager.getInstance().getAll();
            List<Wing> wings = WingManager.getInstance().getAll();

            cbOccupation.setItems(FXCollections.observableArrayList(occupations));
            cbWing.setItems(FXCollections.observableArrayList(wings));
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void initComponents() {
        setTitle("Editor de Prisioneiro");
        setHeaderText("Editor de Prisioneiro");

        cbOccupation.setPrefWidth(300);
        cbWing.setPrefWidth(300);

        setSource(source);
        loadComboItems();

        grid.setPadding(new Insets(500));
        grid.setStyle("-fx-padding: 30;");
        grid.setPrefWidth(500);
        grid.setVgap(15);
        grid.setHgap(15);

        grid.add(lbName, 0, 0, 1, 1);
        grid.add(tfName, 1, 0, 1, 1);

        grid.add(lbCpf, 0, 1, 1, 1);
        grid.add(tfCpf, 1, 1, 1, 1);

        grid.add(lbPhone, 0, 2, 1, 1);
        grid.add(tfPhone, 1, 2, 1, 1);

        grid.add(lbOccupation, 0, 3, 1, 1);
        grid.add(cbOccupation, 1, 3, 1, 1);

        grid.add(lbWing, 0, 4, 1, 1);
        grid.add(cbWing, 1, 4, 1, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent(grid);
    }

    private GridPane grid = new GridPane();

    private Label lbName = new Label("Nome: *");
    private TextField tfName = new TextField();

    private Label lbCpf = new Label("CPF: *");
    private MaskedTextField tfCpf = new MaskedTextField("###.###.###-##");

    private Label lbPhone = new Label("Fone: *");
    private MaskedTextField tfPhone = new MaskedTextField("(##) #####-####");

    private Label lbOccupation = new Label("Cargo: *");
    private ComboBox<Occupation> cbOccupation = new ComboBox();

    private Label lbWing = new Label("Ala: *");
    private ComboBox<Wing> cbWing = new ComboBox();
}
