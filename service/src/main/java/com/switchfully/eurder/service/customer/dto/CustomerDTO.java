package com.switchfully.eurder.service.customer.dto;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.service.address.AddressDTO;

public class CustomerDTO {
    private String customerID;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private AddressDTO address;
    private String phoneNumber;

    public CustomerDTO setCustomerID(String customerID) {
        this.customerID = customerID;
        return this;
    }

    public CustomerDTO setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public CustomerDTO setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public CustomerDTO setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public CustomerDTO setAddress(AddressDTO address) {
        this.address = address;
        return this;
    }

    public CustomerDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCustomerID() {
        return customerID;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
