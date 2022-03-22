//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import sharedrmi.domain.enums.*;
import sharedrmi.domain.valueobjects.*;

public class AlbumDTO implements Serializable {
    private final String title;
    private final BigDecimal price;
    private final int stock;
    private final MediumType mediumType;
    private final LocalDate releaseDate;
    private final AlbumId albumId;
    private final String label;
    private final Set<SongDTO> songs;

    public AlbumDTO(String var1, BigDecimal var2, int var3, MediumType var4, LocalDate var5, AlbumId var6, String var7, Set<SongDTO> var8) {
        this.title = var1;
        this.price = var2;
        this.stock = var3;
        this.mediumType = var4;
        this.releaseDate = var5;
        this.albumId = var6;
        this.label = var7;
        this.songs = var8;
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
}
