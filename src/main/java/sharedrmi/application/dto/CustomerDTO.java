//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;

public class CustomerDTO implements Serializable {
    private final String firstName;
    private final String lastName;

    public CustomerDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static CustomerDTO.CustomerDTOBuilder builder() {
        return new CustomerDTO.CustomerDTOBuilder();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public static class CustomerDTOBuilder {
        private String firstName;
        private String lastName;

        CustomerDTOBuilder() {
        }

        public CustomerDTO.CustomerDTOBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CustomerDTO.CustomerDTOBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(this.firstName, this.lastName);
        }

        public String toString() {
            return "CustomerDTO.CustomerDTOBuilder(firstName=" + this.firstName + ", lastName=" + this.lastName + ")";
        }
    }
}
