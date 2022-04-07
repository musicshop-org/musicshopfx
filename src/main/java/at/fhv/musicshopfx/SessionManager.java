package at.fhv.musicshopfx;

import sharedrmi.communication.rmi.RMIController;
import sharedrmi.communication.rmi.RMIControllerFactory;

import javax.security.auth.login.FailedLoginException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SessionManager {

    private static SessionManager instance;
    private static boolean isLoggedIn;

    private RMIController rmiController;


    private SessionManager(RMIController rmiController) {
        SessionManager.instance = this;
        this.rmiController = rmiController;
    }


    public static SessionManager getInstance() throws NotLoggedInException {
        if (!SessionManager.isLoggedIn) {
            throw new NotLoggedInException("Not logged in! Call SessionManager.login() first");
        }

        return SessionManager.instance;
    }

    public static boolean login(String username, String password) {
        try {
            RMIControllerFactory rmiControllerFactory = (RMIControllerFactory) Naming.lookup("rmi://localhost/RMIControllerFactory");
            RMIController rmiController = rmiControllerFactory.createRMIController(username, password);

            new SessionManager(rmiController);
            SessionManager.isLoggedIn = true;

            return true;

        } catch (MalformedURLException | RemoteException | NotBoundException | FailedLoginException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public RMIController getRMIController() {
        return rmiController;
    }
}