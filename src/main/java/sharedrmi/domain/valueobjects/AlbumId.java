//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.domain.valueobjects;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class AlbumId implements Serializable {
    private final UUID albumId = UUID.randomUUID();

    public AlbumId() {
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            AlbumId albumId1 = (AlbumId)o;
            return Objects.equals(this.albumId, albumId1.albumId);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.albumId});
    }

    public UUID getAlbumId() {
        return this.albumId;
    }
}
