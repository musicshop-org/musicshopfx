//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.communication.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import sharedrmi.application.api.ProductService;
import sharedrmi.application.api.ShoppingCartService;
import sharedrmi.domain.valueobjects.Role;

public interface RMIController extends ProductService, ShoppingCartService, Remote {
    List<Role> getRoles() throws RemoteException;

    String getUsername() throws RemoteException;
}
