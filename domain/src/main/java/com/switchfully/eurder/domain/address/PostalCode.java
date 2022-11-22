package com.switchfully.eurder.domain.address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.regex.Pattern;

@Entity
@Table(name = "POSTAL_CODE")
public class PostalCode {
    @Id
    @Column(name = "POSTAL_CODE")
    private String postalCode;
    @Column(name = "CITY_NAME")
    private String cityName;

    public PostalCode(String postalCode, String cityName) {
        this.postalCode = validatePostalCode(postalCode);
        this.cityName = validateStringValueForNotNullOrEmpty(cityName);
    }

    public PostalCode() {
    }

    public String validatePostalCode(String postalCode) {
        boolean isValidPostalCode = Pattern.matches("[0-9]{4}", postalCode);
        if (!isValidPostalCode) {
            throw new IllegalArgumentException("The provided postal code is not valid");
        }
        return postalCode;
    }

    public String validateStringValueForNotNullOrEmpty(String value) {
        if (value == null || value.trim().length() < 2) {
            throw new IllegalArgumentException("The provided city is not valid");
        }
        return value;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCityName() {
        return cityName;
    }

    @Override
    public String toString() {
        return String.format("%s %s", postalCode, cityName);
    }
}
