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
import sharedrmi.domain.enums.*;


public class SongDTO implements Serializable {
    private final String title;
    private final BigDecimal price;
    private final int stock;
    private final MediumType mediumType;
    private final LocalDate releaseDate;
    private final String genre;
    private final List<ArtistDTO> artists;
    private final Set<AlbumDTO> inAlbum;

    public SongDTO(String var1, BigDecimal var2, int var3, MediumType var4, LocalDate var5, String var6, List<ArtistDTO> var7, Set<AlbumDTO> var8) {
        this.title = var1;
        this.price = var2;
        this.stock = var3;
        this.mediumType = var4;
        this.releaseDate = var5;
        this.genre = var6;
        this.artists = var7;
        this.inAlbum = var8;
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
}
