package at.fhv.musicshopfx;

import sharedrmi.application.dto.AlbumDTO;
import sharedrmi.communication.rmi.RMIController;
import sharedrmi.communication.rmi.RMIControllerFactory;

import javax.security.auth.login.FailedLoginException;
import java.net.MalformedURLException;
import java.nio.file.AccessDeniedException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SessionManager {

    private static SessionManager instance;
    private static boolean isLoggedIn;
    private static String lastSearch = "";
    private static List<AlbumDTO> lastAlbums = new ArrayList<>();

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

    public static boolean login(String username, String password) throws FailedLoginException, AccessDeniedException{
        try {
            RMIControllerFactory rmiControllerFactory = (RMIControllerFactory) Naming.lookup("rmi://localhost/RMIControllerFactory");
            RMIController rmiController = rmiControllerFactory.createRMIController(username, password);
            new SessionManager(rmiController);
            SessionManager.isLoggedIn = true;

            return true;

        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            return false;
        }
    }

    public static void logout() throws NotLoggedInException {
        if (isLoggedIn) {
            SessionManager.instance = null;
            isLoggedIn = false;
            lastSearch = "";
            lastAlbums = new ArrayList<>();
        } else {
            throw new NotLoggedInException("Not logged in! Call SessionManager.login() first");
        }
    }

    public RMIController getRMIController() {
        return rmiController;
    }

    public static String getLastSearch() { return SessionManager.lastSearch; }

    public static void setLastSearch(String lastSearch) {
        SessionManager.lastSearch = lastSearch;
    }

    public static List<AlbumDTO> getLastAlbums() {return lastAlbums; }

    public static void setLastAlbums(List<AlbumDTO> albums) { SessionManager.lastAlbums = albums; }
}