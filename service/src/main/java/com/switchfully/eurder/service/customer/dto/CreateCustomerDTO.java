package com.switchfully.eurder.service.customer.dto;

public class CreateCustomerDTO {
    private String firstname;
    private String lastname;
    private String emailAddress;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String cityName;
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

    public CreateCustomerDTO setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public CreateCustomerDTO setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public CreateCustomerDTO setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public CreateCustomerDTO setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCityName() {
        return cityName;
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
