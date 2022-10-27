package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.address.Address;

import java.time.LocalDate;

public class Customer {
    private static final String CUSTOMER_ID_PREFIX = "CID";
    private static int customer_id_suffix = 1001;
    private final String customerID;
    private final String firstname;
    private final String lastname;
    private final String emailAddress;
    private final Address address;
    private final String phoneNumber;
    private final String password;
    private final Role role;

    public Customer(String firstname, String lastname, String emailAddress, Address address, String phoneNumber, String password, Role role) {
        customerID = CUSTOMER_ID_PREFIX + LocalDate.now().getYear() + customer_id_suffix++;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public boolean doesPasswordMatch(String passwordToMatch) {
        return this.password.equals(passwordToMatch);
    }

    public boolean canHaveAccessTo(Feature feature) {
        return role.containsFeature(feature);
    }
}
