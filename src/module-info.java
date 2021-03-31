module CarandiTechWithSceneBuilder {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens views;
    opens controllers to javafx.fxml;
}