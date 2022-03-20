module at.fhv.musicshopfx.musicshopfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.fhv.musicshopfx.musicshopfx to javafx.fxml;
    exports at.fhv.musicshopfx.musicshopfx;
}