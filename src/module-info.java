module CarandiTechWithSceneBuilder {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires itextpdf;
    requires org.controlsfx.controls;

    opens views;
    opens controllers to javafx.fxml;
}