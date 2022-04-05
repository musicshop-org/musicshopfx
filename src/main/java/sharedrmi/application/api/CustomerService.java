package sharedrmi.application.api;

import sharedrmi.application.dto.CustomerDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CustomerService extends Remote {
    List<CustomerDTO> findCustomersByName(String name) throws RemoteException;
}
