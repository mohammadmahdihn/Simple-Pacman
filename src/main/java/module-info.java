module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires javafx.media;

    opens org.example to javafx.fxml, com.google.gson;
    exports org.example to javafx.graphics, javafx.media, javafx.base;
}
