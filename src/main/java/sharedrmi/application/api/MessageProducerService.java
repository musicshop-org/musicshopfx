//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.api;

import sharedrmi.application.dto.MessageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MessageProducerService extends Remote {
    void publish(List<String> topics, MessageDTO message) throws RemoteException;
}
