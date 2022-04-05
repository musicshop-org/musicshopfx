package sharedrmi.domain.valueobjects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class InvoiceId  implements Serializable {
    private final long invoiceId;

    public InvoiceId() {
        this.invoiceId = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public InvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getInvoiceId() {
        return this.invoiceId;
    }
}
