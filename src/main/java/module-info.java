module com.example.asu_bookstore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.asu_bookstore to javafx.fxml;
    exports com.example.asu_bookstore;
}