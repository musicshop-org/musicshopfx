//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.domain.valueobjects;

import java.io.Serializable;
import java.time.Instant;

public class InvoiceId implements Serializable {
    private final long invoiceId;

    public InvoiceId() {
        this.invoiceId = Instant.now().toEpochMilli();
    }

    public InvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getInvoiceId() {
        return this.invoiceId;
    }
}
