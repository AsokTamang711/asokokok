module com.example.asoktmg123 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.asoktmg123 to javafx.fxml;
    exports com.example.asoktmg123;
}
