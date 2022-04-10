package sharedrmi.communication.rmi;

import java.nio.file.AccessDeniedException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.security.auth.login.FailedLoginException;

public interface RMIControllerFactory extends Remote {
    RMIController createRMIController(String var1, String var2) throws FailedLoginException, RemoteException, AccessDeniedException;
}


