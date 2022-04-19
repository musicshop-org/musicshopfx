//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import javax.naming.NoPermissionException;
import sharedrmi.application.dto.MessageDTO;

public interface MessageProducerService extends Remote {
    void publish(List<String> topics, MessageDTO messageDTO) throws RemoteException;
}
