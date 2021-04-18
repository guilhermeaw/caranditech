module CarandiTechWithSceneBuilder {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens views;
    opens views.components;
    opens controllers to javafx.fxml;
}