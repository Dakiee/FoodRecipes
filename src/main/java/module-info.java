module com.example.foodrecipes {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.foodrecipes to javafx.fxml;
    exports com.example.foodrecipes;
}