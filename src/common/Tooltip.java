package common;

public class Tooltip extends javafx.scene.control.Tooltip {
    private boolean isMultiline = false;

    public Tooltip() {
        super();
    }

    public Tooltip(String text) {
        super(text);
    }

    public void setMultiline(boolean value) {
        this.isMultiline = value;

        if (isMultiline) {
            this.setMaxWidth(240);
            this.setWrapText(true);
        }
    }
}
