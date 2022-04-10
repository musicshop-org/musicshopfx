//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import sharedrmi.application.dto.InvoiceDTO;
import sharedrmi.application.dto.InvoiceLineItemDTO;
import sharedrmi.application.exceptions.InvoiceNotFoundException;
import sharedrmi.domain.valueobjects.InvoiceId;

public interface InvoiceService extends Remote {
    InvoiceDTO findInvoiceById(InvoiceId var1) throws RemoteException, InvoiceNotFoundException;

    void createInvoice(InvoiceDTO var1) throws RemoteException;

    void returnInvoiceLineItem(InvoiceId var1, InvoiceLineItemDTO var2, int var3) throws RemoteException, InvoiceNotFoundException;
}