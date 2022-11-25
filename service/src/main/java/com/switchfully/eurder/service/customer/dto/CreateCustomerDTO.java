package com.switchfully.eurder.service.customer.dto;

import com.switchfully.eurder.service.address.AddressDTO;

public class CreateCustomerDTO {
    private String firstname;
    private String lastname;
    private String emailAddress;
    private AddressDTO address;
    private String countryCode;
    private String localNumber;
    private String password;

    public CreateCustomerDTO setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public CreateCustomerDTO setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public CreateCustomerDTO setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public CreateCustomerDTO setAddress(AddressDTO address) {
        this.address = address;
        return this;
    }

    public CreateCustomerDTO setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public CreateCustomerDTO setLocalNumber(String localNumber) {
        this.localNumber = localNumber;
        return this;
    }

    public CreateCustomerDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLocalNumber() {
        return localNumber;

    }

    public String getPassword() {
        return password;
    }
}
