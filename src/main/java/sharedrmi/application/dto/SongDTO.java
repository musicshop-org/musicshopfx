//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import sharedrmi.domain.enums.MediumType;

public class SongDTO implements Serializable {
    private final String title;
    private final BigDecimal price;
    private final int stock;
    private final MediumType mediumType;
    private final LocalDate releaseDate;
    private final String genre;
    private final List<ArtistDTO> artists;
    private final Set<AlbumDTO> inAlbum;

    public SongDTO(String title, BigDecimal price, int stock, MediumType mediumType, LocalDate releaseDate, String genre, List<ArtistDTO> artists, Set<AlbumDTO> inAlbum) {
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.mediumType = mediumType;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.artists = artists;
        this.inAlbum = inAlbum;
    }

    public static SongDTO.SongDTOBuilder builder() {
        return new SongDTO.SongDTOBuilder();
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

    public String getGenre() {
        return this.genre;
    }

    public List<ArtistDTO> getArtists() {
        return this.artists;
    }

    public Set<AlbumDTO> getInAlbum() {
        return this.inAlbum;
    }

    public static class SongDTOBuilder {
        private String title;
        private BigDecimal price;
        private int stock;
        private MediumType mediumType;
        private LocalDate releaseDate;
        private String genre;
        private List<ArtistDTO> artists;
        private Set<AlbumDTO> inAlbum;

        SongDTOBuilder() {
        }

        public SongDTO.SongDTOBuilder title(String title) {
            this.title = title;
            return this;
        }

        public SongDTO.SongDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public SongDTO.SongDTOBuilder stock(int stock) {
            this.stock = stock;
            return this;
        }

        public SongDTO.SongDTOBuilder mediumType(MediumType mediumType) {
            this.mediumType = mediumType;
            return this;
        }

        public SongDTO.SongDTOBuilder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public SongDTO.SongDTOBuilder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public SongDTO.SongDTOBuilder artists(List<ArtistDTO> artists) {
            this.artists = artists;
            return this;
        }

        public SongDTO.SongDTOBuilder inAlbum(Set<AlbumDTO> inAlbum) {
            this.inAlbum = inAlbum;
            return this;
        }

        public SongDTO build() {
            return new SongDTO(this.title, this.price, this.stock, this.mediumType, this.releaseDate, this.genre, this.artists, this.inAlbum);
        }

        public String toString() {
            return "SongDTO.SongDTOBuilder(title=" + this.title + ", price=" + this.price + ", stock=" + this.stock + ", mediumType=" + this.mediumType + ", releaseDate=" + this.releaseDate + ", genre=" + this.genre + ", artists=" + this.artists + ", inAlbum=" + this.inAlbum + ")";
        }
    }
}
