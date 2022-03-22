module at.fhv.musicshopfx.musicshopfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires sharedrmi;


    opens at.fhv.musicshopfx.musicshopfx to javafx.fxml;
    exports at.fhv.musicshopfx.musicshopfx;
}