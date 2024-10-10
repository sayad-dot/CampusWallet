module org.example.campuswallet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;



    opens org.example.campuswallet to javafx.fxml;
    exports org.example.campuswallet;
}