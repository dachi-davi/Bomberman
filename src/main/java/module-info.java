module com.example.bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.media;


    opens com.example.bomberman to javafx.fxml;
    exports com.example.bomberman;
}