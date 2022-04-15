//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MessageProducerService extends Remote {
    void publish(List<String> topics, String messageTitle, String messageText, long expirationDays) throws RemoteException;
}
