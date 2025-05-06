module com.toto.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires spring.beans;
    requires com.toto.backend;
    requires spring.context;
    requires spring.boot;
    requires org.jfxtras.styles.jmetro;
    requires java.sql;
    requires spring.core;

    opens com.toto.ui to javafx.fxml, spring.core, spring.beans, spring.context;
    exports com.toto.ui;
}