package com.switchfully.eurder.domain;

public class CustomerBuilder {
    private String firstname;
    private String lastname;
    private String emailAddress;
    private Address address;
    private String phoneNumber;

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

    public Customer build() {
        return new Customer(firstname, lastname, emailAddress, address, phoneNumber);
    }
}
