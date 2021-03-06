module com.example.greatestquotesofpolytech {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.greatestquotes to javafx.fxml;
    exports com.greatestquotes;
    exports com.greatestquotes.controllers;
    opens com.greatestquotes.controllers to javafx.fxml;
    exports com.greatestquotes.models;
    opens com.greatestquotes.models to javafx.fxml;
}