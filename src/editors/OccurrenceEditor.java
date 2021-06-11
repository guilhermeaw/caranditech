package editors;

import common.DefaultEditor;
import common.EditorCallback;
import common.MaskedTextField;
import db.managers.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.*;
import utils.ApplicationUtilities;
import utils.DateUtils;
import validators.EmptyValidator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class OccurrenceEditor extends DefaultEditor<Occurrence> {
    public OccurrenceEditor(EditorCallback<Occurrence> callback) {
        super(callback);

        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        if (!EmptyValidator.validate(tfTitle.getText())) {
            errors.add("É necessário informar um título");
        }

        if (cbOccurrenceType.getValue() == null) {
            errors.add("É necessário informar um tipo de ocorrência");
        }

        if (cbPrisoner.getValue() == null) {
            errors.add("É necessário informar um prisioneiro");
        }

        if (cbAuthor.getValue() == null) {
            errors.add("É necessário informar um autor");
        }

        if (dpCreatedDate.getValue() == null) {
            errors.add("É necessário informar uma data de criação");
        }
    }

    @Override
    protected void obtainInput() {
        source.setTitle(tfTitle.getText());
        source.setOccurrenceTypeId(cbOccurrenceType.getValue().getId());
        source.setPrisonerId(cbPrisoner.getValue().getId());
        source.setAuthorId(cbAuthor.getValue().getId());
        source.setCreatedDate(DateUtils.getDateByLocalDate(dpCreatedDate.getValue()));
        source.setDescription(tfDescription.getText());
    }

    @Override
    protected void setSource(Occurrence source) {
        if (source.getId() != 0) {
            tfTitle.setText(source.getTitle());
            cbOccurrenceType.setValue(getOccurrenceTypeById(source.getOccurrenceTypeId()));
            cbPrisoner.setValue(getPrisonerById(source.getPrisonerId()));
            cbAuthor.setValue(getUserById(source.getAuthorId()));
            dpCreatedDate.setValue(DateUtils.getLocalDateByDate(source.getCreatedDate()));
            tfDescription.setText(source.getDescription());
        } else {
            cbAuthor.setValue(ApplicationUtilities.getInstance().getActiveUser());
            dpCreatedDate.setValue(LocalDate.now());
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

    private User getUserById(int id) {
        try {
            return UserManager.getInstance().getById(id);
        } catch (Exception e) {
            handleException(e);
        }

        return null;
    }

    private OccurrenceType getOccurrenceTypeById(int id) {
        try {
            return OccurrenceTypeManager.getInstance().getById(id);
        } catch (Exception e) {
            handleException(e);
        }

        return null;
    }

    private void loadComboItems() {
        try {
            List<Prisoner> prisoners = PrisonerManager.getInstance().getAll();
            List<User> users = UserManager.getInstance().getAll();
            List<OccurrenceType> occurrenceTypes = OccurrenceTypeManager.getInstance().getAll();

            cbPrisoner.setItems(FXCollections.observableArrayList(prisoners));
            cbAuthor.setItems(FXCollections.observableArrayList(users));
            cbOccurrenceType.setItems(FXCollections.observableArrayList(occurrenceTypes));
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void initComponents() {
        setTitle("Editor de Ocorrência");
        setHeaderText("Editor de Ocorrência");

        cbPrisoner.setPrefWidth(300);
        cbAuthor.setPrefWidth(300);
        cbOccurrenceType.setPrefWidth(300);
        cbAuthor.setDisable(true);
        dpCreatedDate.setDisable(true);

        setSource(source);
        loadComboItems();

        grid.setPadding(new Insets(500));
        grid.setStyle("-fx-padding: 30;");
        grid.setPrefWidth(500);
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

        grid.add(lbCreatedDate, 0, 4, 1, 1);
        grid.add(dpCreatedDate, 1, 4, 1, 1);

        grid.add(lbDescription, 0, 5, 1, 1);
        grid.add(tfDescription, 1, 5, 1, 1);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(new ColumnConstraints(), cc);
        getDialogPane().setContent(grid);
    }

    private GridPane grid = new GridPane();

    private Label lbTitle = new Label("Título: *");
    private TextField tfTitle = new TextField();

    private Label lbOccurrenceType = new Label("Tipo de Ocorrência: *");
    private ComboBox<OccurrenceType> cbOccurrenceType = new ComboBox();

    private Label lbPrisoner = new Label("Prisioneiro: *");
    private ComboBox<Prisoner> cbPrisoner = new ComboBox();

    private Label lbAuthor = new Label("Autor: *");
    private ComboBox<User> cbAuthor = new ComboBox();

    private Label lbCreatedDate = new Label("Data de criação: *");
    private DatePicker dpCreatedDate = new DatePicker();

    private Label lbDescription = new Label("Descrição:");
    private TextField tfDescription = new TextField();
}
