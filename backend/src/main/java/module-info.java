open module com.toto.backend {
    requires jakarta.persistence;
    requires spring.context;
    requires spring.boot;
    requires spring.data.jpa;
    requires spring.beans;
    requires static lombok;
    requires jakarta.validation;
    requires spring.tx;
    requires spring.data.commons;
    requires spring.boot.autoconfigure;
    requires org.hibernate.orm.core;
    requires spring.aop;

    // Export your packages so the UI module can access them
    exports com.toto.backend.entities;
    exports com.toto.backend.services;
    exports com.toto.backend.services.interfaces;
    exports com.toto.backend.repositories;
    exports com.toto.backend.entities.enums;

    // Open all your entity classes for reflectionwhy does the current system n

    // Open repositories to Spring



    // Allow Spring to access these packages
    exports com.toto.backend;
}
