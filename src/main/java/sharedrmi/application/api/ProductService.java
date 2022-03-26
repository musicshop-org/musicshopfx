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

public interface ProductService extends Remote {
    List<AlbumDTO> findAlbumsBySongTitle(String var1) throws RemoteException;

    List<SongDTO> findSongsByTitle(String var1) throws RemoteException;

    List<ArtistDTO> findArtistsByName(String var1) throws RemoteException;
}
