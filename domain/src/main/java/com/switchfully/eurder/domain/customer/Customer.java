package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.address.Address;

import java.time.LocalDate;

public class Customer {
    private static final String CUSTOMER_ID_PREFIX = "CID";
    private static int customer_id_suffix = 1001;
    private String customerID;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private Address address;
    private String phoneNumber;

    public Customer(String firstname, String lastname, String emailAddress, Address address, String phoneNumber) {
        customerID = CUSTOMER_ID_PREFIX + LocalDate.now().getYear() + customer_id_suffix++;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public String getFullAddress() {
        return address.getFullAddressAsString();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
