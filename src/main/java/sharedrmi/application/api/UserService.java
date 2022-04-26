//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.api;

import sharedrmi.application.exceptions.UserNotFoundException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService extends Remote {
    List<String> getAllTopics() throws RemoteException;
    List<String> getSubscribedTopicsForUser(String var1) throws RemoteException;
    void changeLastViewed(String var1, LocalDateTime var2) throws UserNotFoundException, RemoteException;
    LocalDateTime getLastViewedForUser(String var1) throws RemoteException, UserNotFoundException;
}
