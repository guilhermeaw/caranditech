package common;

import common.EditorCallback;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Callback;
import services.AlertService;
import utils.ApplicationUtilities;

import java.util.ArrayList;
import java.util.List;

public abstract class DefaultEditor<T> extends Dialog<T> {
    public EditorCallback callback;
    public T source;

    public DefaultEditor( EditorCallback<T> callback ) {
        this.callback = callback;
        source = callback.getSource();

        initComponents();
    }

    private boolean onSave() {
        try {
            List<String> errorList  = new ArrayList<>();

            validateInput( errorList );

            if ( !errorList.isEmpty() ) {
                String message = ApplicationUtilities.getInstance().formatErrorMessage(errorList);

                AlertService.showWarning(message);

                return false;
            } else {
                obtainInput();
                callback.onEvent();
            }
        } catch ( Exception e ) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return true;
    }

    protected void onCancel() {}

    private void initComponents() {
        setTitle( "Editor" );
        setHeaderText( "Editor de Items" );
        setResizable( false );

        getDialogPane().getButtonTypes().addAll( cancelBtn, saveBtn );

        setOnCloseRequest( new EventHandler() {
            @Override
            public void handle( Event t ) {
                if (  selectedBtn == saveBtn ) {
                    if ( ! onSave() ) {
                        t.consume();
                    }
                } else {
                    onCancel();
                }
            }
        } );

        setResultConverter( new Callback() {
            @Override
            public Object call( Object p ) {
                return selectedBtn = (ButtonType) p;
            }
        } );
    }

    public void open() {
        showAndWait();
    }

    protected void handleException(Exception e) {
        ApplicationUtilities.getInstance().handleException(e);
    }

    protected abstract void validateInput( List<String> errors ) throws Exception;
    protected abstract void obtainInput();
    protected abstract void setSource( T source );

    private ButtonType saveBtn   = new ButtonType( "Salvar",   ButtonBar.ButtonData.OK_DONE );
    private ButtonType cancelBtn = new ButtonType( "Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE );
    private ButtonType selectedBtn;
}
