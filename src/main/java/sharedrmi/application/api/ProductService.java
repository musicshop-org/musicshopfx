package sharedrmi.application.api;

import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.ArtistDTO;
import sharedrmi.application.dto.SongDTO;
import sharedrmi.application.exceptions.AlbumNotFoundException;
import sharedrmi.domain.enums.MediumType;

import javax.naming.NoPermissionException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductService extends Remote {

    List<AlbumDTO> findAlbumsBySongTitle(String title) throws RemoteException;

    AlbumDTO findAlbumByAlbumTitleAndMedium(String title, MediumType mediumType) throws RemoteException, AlbumNotFoundException;

    List<SongDTO> findSongsByTitle(String title) throws RemoteException;

    List<ArtistDTO> findArtistsByName(String name) throws RemoteException;

    void decreaseStockOfAlbum(String title, MediumType mediumType, int decreaseAmount) throws RemoteException, NoPermissionException;

    void increaseStockOfAlbum(String title, MediumType mediumType, int decreaseAmount) throws RemoteException, NoPermissionException;

}
