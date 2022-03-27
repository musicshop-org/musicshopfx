//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;

public class ArtistDTO implements Serializable {
    private final String name;

    public ArtistDTO(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public static ArtistDTO.ArtistDTOBuilder builder() {
        return new ArtistDTO.ArtistDTOBuilder();
    }

    public String getName() {
        return this.name;
    }

    public static class ArtistDTOBuilder {
        private String name;

        ArtistDTOBuilder() {
        }

        public ArtistDTO.ArtistDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ArtistDTO build() {
            return new ArtistDTO(this.name);
        }

        public String toString() {
            return "ArtistDTO.ArtistDTOBuilder(name=" + this.name + ")";
        }
    }
}
