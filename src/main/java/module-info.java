module com.example.cafeteriahilosinterfaz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cafeteriahilosinterfaz to javafx.fxml;
    exports com.example.cafeteriahilosinterfaz;
}