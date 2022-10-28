package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.address.Address;

import java.time.LocalDate;
import java.util.regex.Pattern;

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
        this.lastname = validateName(lastname);
        this.emailAddress = validateEmailAddress(emailAddress);
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = validatePassword(password);
        this.role = role;
    }

    private String validateEmailAddress(String emailAddress) {
        boolean isValidEmailAddress = Pattern.matches("\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b", emailAddress);
        if (!isValidEmailAddress) {
            throw new IllegalArgumentException("The provided email address is not valid");
        }
        return emailAddress;
    }

    private String validateName(String name) {
        if (name == null || name.trim().length() < 1) {
            throw new IllegalArgumentException("The provided name is not valid");
        }
        return name;
    }

    public String validatePassword(String password) {
        if (password == null || password.trim().length() < 5) {
            throw new IllegalArgumentException("The provided password is not long enough");
        }
        return password;
    }

    public boolean doesPasswordMatch(String passwordToMatch) {
        return this.password.equals(passwordToMatch);
    }

    public boolean canHaveAccessTo(Feature feature) {
        return role.containsFeature(feature);
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
}
