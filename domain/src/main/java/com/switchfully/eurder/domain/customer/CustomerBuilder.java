package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.address.Address;

public class CustomerBuilder {
    private String firstname;
    private String lastname;
    private String emailAddress;
    private Address address;
    private String phoneNumber;

    private String password;

    public CustomerBuilder setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public CustomerBuilder setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public CustomerBuilder setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public CustomerBuilder setAddress(Address address) {
        this.address = address;
        return this;
    }

    public CustomerBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public CustomerBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public Customer build() {
        return new Customer(firstname, lastname, emailAddress, address, phoneNumber, password);
    }
}
