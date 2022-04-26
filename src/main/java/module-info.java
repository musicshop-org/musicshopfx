module at.fhv.musicshopfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.desktop;
    requires java.naming;
    requires activemq.all;
    //requires org.apache.logging.log4j.core;
    //    requires sharedrmi;

    opens sharedrmi.application.dto to javafx.base;
    opens sharedrmi.application.exceptions to java.base;

    opens sharedrmi.communication.rmi to java.base;

    opens sharedrmi.domain.enums to javafx.base;
    opens sharedrmi.domain.valueobjects to javafx.base;


    exports sharedrmi.domain;
    exports sharedrmi.domain.enums;
    exports sharedrmi.domain.valueobjects;

    exports sharedrmi.communication.rmi;

    exports sharedrmi.application.dto;
    exports sharedrmi.application.api;
    exports sharedrmi.application.exceptions;

    exports at.fhv.musicshopfx;

    opens sharedrmi.domain to javafx.fxml;
    opens at.fhv.musicshopfx to javafx.base, javafx.fxml;
    opens sharedrmi.application.api to javafx.base, javafx.fxml;
}