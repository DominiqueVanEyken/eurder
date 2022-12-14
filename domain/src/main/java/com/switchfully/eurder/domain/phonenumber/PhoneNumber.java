package com.switchfully.eurder.domain.phonenumber;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.Objects;
import java.util.regex.Pattern;
@Embeddable
public class PhoneNumber {
    @Transient
    private CountryCode countryCode;
    @Transient
    private String localNumber;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    public PhoneNumber(CountryCode countryCode, String localNumber) {
        this.countryCode = countryCode;
        this.localNumber = validateLocalNumber(localNumber);
        phoneNumber = String.format("%s %s", this.countryCode, this.localNumber);
    }

    public PhoneNumber(String countryCode, String localNumber) {
        this(CountryCode.findCountryCode(countryCode), localNumber);
    }

    public PhoneNumber() {
    }

    public String validateLocalNumber(String localNumber) {
        String formatedLocalNumber = localNumber.startsWith("0") ? localNumber.trim().replace(" ", "").substring(1) : localNumber.trim().replace(" ", "");
        boolean isValidLocalNumber = Pattern.matches("[0-9]{8,9}", formatedLocalNumber);
        if (!isValidLocalNumber) {
            throw new IllegalArgumentException("The provided phone number is not valid");
        }
        if (formatedLocalNumber.length() == 8) {
            return String.format("%s %s %s %s", formatedLocalNumber.substring(0,2), formatedLocalNumber.substring(2,4), formatedLocalNumber.substring(4, 6), formatedLocalNumber.substring(6,8));
        }
        return String.format("%s %s %s %s", formatedLocalNumber.substring(0,3), formatedLocalNumber.substring(3,5), formatedLocalNumber.substring(5, 7), formatedLocalNumber.substring(7,9));
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return countryCode == that.countryCode && Objects.equals(localNumber, that.localNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, localNumber);
    }
}
