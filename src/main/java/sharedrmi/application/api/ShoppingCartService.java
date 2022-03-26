package sharedrmi.application.api;

import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.application.dto.ShoppingCartDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ShoppingCartService extends Remote {
    void addProductToCart(AlbumDTO album, int amount) throws RemoteException;
    ShoppingCartDTO displayCart() throws RemoteException;
}
