module com.example.epilanggui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires kotlin.stdlib;

    opens com.example.epilanggui to javafx.fxml;
    exports com.example.epilanggui;
    exports com.example.epilanggui.core;
    opens com.example.epilanggui.core to javafx.fxml;
}