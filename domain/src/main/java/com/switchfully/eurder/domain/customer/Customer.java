package com.switchfully.eurder.domain.customer;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.regex.Pattern;
@Entity
@Table(name = "CUSTOMER")
public class Customer {
    private static final String CUSTOMER_ID_PREFIX = "CID";
    private static int customer_id_suffix = 1001;
    @Id
    @Column(name = "CUSTOMER_ID")
    private String customerID;
    @Column(name = "FIRST_NAME")
    private String firstname;
    @Column(name = "LAST_NAME")
    private String lastname;
    @Column(name = "EMAIL")
    private String emailAddress;
    @Embedded
    private Address address;
    @Embedded
    private PhoneNumber phoneNumber;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ROLE")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Customer(String firstname, String lastname, String emailAddress, Address address, PhoneNumber phoneNumber, String password, Role role) {
        customerID = CUSTOMER_ID_PREFIX + LocalDate.now().getYear() + customer_id_suffix++;
        this.firstname = firstname;
        this.lastname = validateName(lastname);
        this.emailAddress = validateEmailAddress(emailAddress);
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = validatePassword(password);
        this.role = role;
    }

    public Customer() {
    }

    private String validateEmailAddress(String emailAddress) {
        String exceptionMessage = "The provided email address is not valid";
        if (emailAddress == null) {
            throw new IllegalArgumentException(exceptionMessage);
        }
        boolean isValidEmailAddress = Pattern.matches("\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b", emailAddress);
        if (!isValidEmailAddress) {
            throw new IllegalArgumentException(exceptionMessage);
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
        if (address == null) {
            return "";
        }
        return address.getFullAddressAsString();
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        if (phoneNumber == null) {
            return "";
        }
        return phoneNumber.getPhoneNumber();
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return String.format("%s{customerID:%s}", role, customerID);
    }
}
