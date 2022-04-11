//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.ArtistDTO;
import sharedrmi.application.dto.SongDTO;
import sharedrmi.application.exceptions.AlbumNotFoundException;
import sharedrmi.domain.enums.MediumType;

public interface ProductService extends Remote {
    List<AlbumDTO> findAlbumsBySongTitle(String var1) throws RemoteException;

    AlbumDTO findAlbumByAlbumTitleAndMedium(String var1, MediumType var2) throws RemoteException, AlbumNotFoundException;

    List<SongDTO> findSongsByTitle(String var1) throws RemoteException;

    List<ArtistDTO> findArtistsByName(String var1) throws RemoteException;

    void decreaseStockOfAlbum(String var1, MediumType var2, int var3);
}
