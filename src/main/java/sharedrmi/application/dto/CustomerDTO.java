//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sharedrmi.application.dto;

import java.io.Serializable;

public class CustomerDTO implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String email;

    public CustomerDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public String getEmail() {
        return this.email;
    }

    public static class CustomerDTOBuilder {
        private String firstName;
        private String lastName;
        private String email;

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

        public CustomerDTO.CustomerDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(this.firstName, this.lastName, this.email);
        }

        public String toString() {
            return "CustomerDTO.CustomerDTOBuilder(firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ")";
        }
    }
}
