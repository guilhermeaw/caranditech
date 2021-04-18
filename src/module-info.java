module CarandiTechWithSceneBuilder {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens views;
    opens controllers to javafx.fxml;
}