//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import javax.jms.JMSException;
import javax.naming.NoPermissionException;

public interface MessageProducerService extends Remote {
    void publish(List<String> var1, String var2, String var3) throws RemoteException, JMSException, NoPermissionException;
}
