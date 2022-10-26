module com.example.foodrecipes {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.foodrecipes to javafx.fxml;
    exports com.example.foodrecipes;
}