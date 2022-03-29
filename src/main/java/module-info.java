module at.fhv.musicshopfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.desktop;
    // requires sharedrmi;

    opens at.fhv.musicshopfx to javafx.fxml;
   // opens at.fhv.musicshopfx.view.musicsearchscene to javafx.fxml;

    opens sharedrmi.application.dto to javafx.base;
    opens sharedrmi.application.api to javafx.base;
    opens sharedrmi.domain.enums to javafx.base;
    opens sharedrmi.domain.valueobjects to javafx.base;

    //exports at.fhv.musicshopfx.view.musicsearchscene to javafx.fxml;
    exports at.fhv.musicshopfx;
    exports sharedrmi.domain;
    opens sharedrmi.domain to javafx.fxml;
}