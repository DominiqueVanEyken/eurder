package com.switchfully.eurder.service.dto;

public class CreateCustomerDTO {
    private String firstname;
    private String lastname;
    private String emailAddress;
    private String streetName;
    private int streetNumber;
    private String postalCode;
    private String cityName;
    private String phoneNumber;

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

    public CreateCustomerDTO setStreetNumber(int streetNumber) {
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

    public CreateCustomerDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
