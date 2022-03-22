module at.fhv.musicshopfx.musicshopfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
   // requires sharedrmi;
//    requires sharedrmi;


    opens at.fhv.musicshopfx.musicshopfx to javafx.fxml;
    opens sharedrmi.application.dto to javafx.base;
    opens sharedrmi.application.api to javafx.base;
    opens sharedrmi.domain.enums to javafx.base;
    opens sharedrmi.domain.valueobjects to javafx.base;
    exports at.fhv.musicshopfx.musicshopfx;
}