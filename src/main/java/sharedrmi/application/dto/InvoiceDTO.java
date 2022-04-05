package sharedrmi.application.dto;

import sharedrmi.domain.enums.PaymentMethod;
import sharedrmi.domain.valueobjects.InvoiceId;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class InvoiceDTO  implements Serializable {
    private final InvoiceId invoiceId;
    private final List<InvoiceLineItemDTO> invoiceLineItems;
    private final PaymentMethod paymentMethod;
    private final LocalDate date;

    public InvoiceDTO(InvoiceId invoiceId, List<InvoiceLineItemDTO> invoiceLineItems, PaymentMethod paymentMethod, LocalDate date) {
        this.invoiceId = invoiceId;
        this.invoiceLineItems = invoiceLineItems;
        this.paymentMethod = paymentMethod;
        this.date = date;
    }

    public static InvoiceDTO.InvoiceDTOBuilder builder() {
        return new InvoiceDTO.InvoiceDTOBuilder();
    }

    public InvoiceId getInvoiceId() {
        return this.invoiceId;
    }

    public List<InvoiceLineItemDTO> getInvoiceLineItems() {
        return this.invoiceLineItems;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public static class InvoiceDTOBuilder {
        private InvoiceId invoiceId;
        private List<InvoiceLineItemDTO> invoiceLineItems;
        private PaymentMethod paymentMethod;
        private LocalDate date;

        InvoiceDTOBuilder() {
        }

        public InvoiceDTO.InvoiceDTOBuilder invoiceId(InvoiceId invoiceId) {
            this.invoiceId = invoiceId;
            return this;
        }

        public InvoiceDTO.InvoiceDTOBuilder invoiceLineItems(List<InvoiceLineItemDTO> invoiceLineItems) {
            this.invoiceLineItems = invoiceLineItems;
            return this;
        }

        public InvoiceDTO.InvoiceDTOBuilder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public InvoiceDTO.InvoiceDTOBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public InvoiceDTO build() {
            return new InvoiceDTO(this.invoiceId, this.invoiceLineItems, this.paymentMethod, this.date);
        }

        public String toString() {
            return "InvoiceDTO.InvoiceDTOBuilder(invoiceId=" + this.invoiceId + ", invoiceLineItems=" + this.invoiceLineItems + ", paymentMethod=" + this.paymentMethod + ", date=" + this.date + ")";
        }
    }
}
