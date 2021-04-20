package common;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.Occupation;
import validators.EmptyValidator;

import java.util.List;

public class OccupationEditor extends DefaultEditor<Occupation> {
    public OccupationEditor(EditorCallback<Occupation> callback) {
        super(callback);

        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) {
        if (!EmptyValidator.validate(tfName.getText())) {
            errors.add("É necessário informar um nome");
        }
    }

    @Override
    protected void obtainInput() {
        source.setName(tfName.getText());
        source.setDescription(tfDescription.getText());
    }

    @Override
    protected void setSource(Occupation source) {
        if (source.getId() != 0) {
            tfName.setText(source.getName());
            tfDescription.setText(source.getDescription());
        }
    }

    private void initComponents() {
        setTitle("Editor de Ala");
        setHeaderText("Editor de Ala");

        setSource(source);

        grid.setPadding(new Insets( 500 ));
        grid.setStyle("-fx-padding: 30;");
        grid.setPrefWidth(350);
        grid.setVgap(15);
        grid.setHgap(15);

        grid.add(lbName, 0, 0, 1, 1);
        grid.add(tfName, 1, 0, 1, 1);

        grid.add(lbDescription, 0, 1, 1, 1);
        grid.add(tfDescription, 1, 1, 1, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent( grid );
    }

    private GridPane grid = new GridPane();

    private Label lbName = new Label("Nome: *");
    private TextField tfName = new TextField();

    private Label lbDescription = new Label("Descrição:");
    private TextField tfDescription = new TextField();
}
