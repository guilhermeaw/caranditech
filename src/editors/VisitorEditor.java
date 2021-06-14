package editors;

import common.DefaultEditor;
import common.EditorCallback;
import common.MaskedTextField;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.Visitor;
import validators.CpfValidator;
import validators.EmptyValidator;
import validators.PhoneValidator;

import java.util.List;

public class VisitorEditor extends DefaultEditor<Visitor> {
    public VisitorEditor(EditorCallback<Visitor> callback) {
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

        if (!PhoneValidator.validate(tfPhone.getPlainText())) {
            errors.add("É necessário informar um telefone válido");
        }
    }

    @Override
    protected void obtainInput() {
        source.setName(tfName.getText());
        source.setCpf(tfCpf.getPlainText());
        source.setPhone(tfPhone.getPlainText());
    }

    @Override
    protected void setSource(Visitor source) {
        if (source.getId() != 0) {
            tfName.setText(source.getName());
            tfCpf.setPlainText(source.getCpf());
            tfPhone.setPlainText(source.getPhone());
        }
    }

    private void initComponents() {
        setTitle("Editor de Visitante");
        setHeaderText("Editor de Visitante");

        setSource(source);

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
}
