package editors;

import common.DefaultEditor;
import common.EditorCallback;
import db.managers.WingManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.Cell;
import models.Wing;
import validators.EmptyValidator;

import java.util.List;

public class CellEditor extends DefaultEditor<Cell> {
    public CellEditor(EditorCallback<Cell> callback) {
        super(callback);

        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        if (!EmptyValidator.validate(tfName.getText())) {
            errors.add("É necessário informar um nome");
        }

        if (cbWing.getValue() == null) {
            errors.add("É necessário informar uma ala");
        }
    }

    @Override
    protected void obtainInput() {
        source.setName(tfName.getText());
        source.setWingId(cbWing.getValue().getId());
    }

    @Override
    protected void setSource(Cell source) {
        if (source.getId() != 0) {
            tfName.setText(source.getName());
            cbWing.setValue(getWingById(source.getWingId()));
        }
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
            List<Wing> wings = WingManager.getInstance().getAll();

            cbWing.setItems(FXCollections.observableArrayList(wings));
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void initComponents() {
        setTitle("Editor de Prisioneiro");
        setHeaderText("Editor de Prisioneiro");

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

        grid.add(lbWing, 0, 1, 1, 1);
        grid.add(cbWing, 1, 1, 1, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent(grid);
    }

    private GridPane grid = new GridPane();

    private Label lbName = new Label("Nome: *");
    private TextField tfName = new TextField();

    private Label lbWing = new Label("Ala: *");
    private ComboBox<Wing> cbWing = new ComboBox();
}
