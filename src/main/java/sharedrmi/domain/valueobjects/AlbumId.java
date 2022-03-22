//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.domain.valueobjects;

import java.io.Serializable;
import java.util.UUID;

public class AlbumId implements Serializable {
    private final UUID albumId = UUID.randomUUID();

    public AlbumId() {
    }

    public UUID getAlbumId() {
        return this.albumId;
    }
}
