//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import sharedrmi.domain.enums.MediumType;
import sharedrmi.domain.valueobjects.AlbumId;

public class AlbumDTO implements Serializable {
    private final String title;
    private final BigDecimal price;
    private final int stock;
    private final MediumType mediumType;
    private final LocalDate releaseDate;
    private final AlbumId albumId;
    private final String label;
    private final Set<SongDTO> songs;

    public AlbumDTO(String title, BigDecimal price, int stock, MediumType mediumType, LocalDate releaseDate, AlbumId albumId, String label, Set<SongDTO> songs) {
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.mediumType = mediumType;
        this.releaseDate = releaseDate;
        this.albumId = albumId;
        this.label = label;
        this.songs = songs;
    }

    public static AlbumDTO.AlbumDTOBuilder builder() {
        return new AlbumDTO.AlbumDTOBuilder();
    }

    public String getTitle() {
        return this.title;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public MediumType getMediumType() {
        return this.mediumType;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public AlbumId getAlbumId() {
        return this.albumId;
    }

    public String getLabel() {
        return this.label;
    }

    public Set<SongDTO> getSongs() {
        return this.songs;
    }

    public static class AlbumDTOBuilder {
        private String title;
        private BigDecimal price;
        private int stock;
        private MediumType mediumType;
        private LocalDate releaseDate;
        private AlbumId albumId;
        private String label;
        private Set<SongDTO> songs;

        AlbumDTOBuilder() {
        }

        public AlbumDTO.AlbumDTOBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AlbumDTO.AlbumDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public AlbumDTO.AlbumDTOBuilder stock(int stock) {
            this.stock = stock;
            return this;
        }

        public AlbumDTO.AlbumDTOBuilder mediumType(MediumType mediumType) {
            this.mediumType = mediumType;
            return this;
        }

        public AlbumDTO.AlbumDTOBuilder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public AlbumDTO.AlbumDTOBuilder albumId(AlbumId albumId) {
            this.albumId = albumId;
            return this;
        }

        public AlbumDTO.AlbumDTOBuilder label(String label) {
            this.label = label;
            return this;
        }

        public AlbumDTO.AlbumDTOBuilder songs(Set<SongDTO> songs) {
            this.songs = songs;
            return this;
        }

        public AlbumDTO build() {
            return new AlbumDTO(this.title, this.price, this.stock, this.mediumType, this.releaseDate, this.albumId, this.label, this.songs);
        }

        public String toString() {
            return "AlbumDTO.AlbumDTOBuilder(title=" + this.title + ", price=" + this.price + ", stock=" + this.stock + ", mediumType=" + this.mediumType + ", releaseDate=" + this.releaseDate + ", albumId=" + this.albumId + ", label=" + this.label + ", songs=" + this.songs + ")";
        }
    }
}
