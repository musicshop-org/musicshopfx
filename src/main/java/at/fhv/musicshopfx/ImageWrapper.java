package at.fhv.musicshopfx;

import javafx.scene.image.ImageView;

public class ImageWrapper {

    private ImageView image;

    public ImageWrapper(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
