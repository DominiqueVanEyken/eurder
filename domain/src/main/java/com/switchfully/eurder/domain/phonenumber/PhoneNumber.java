package com.switchfully.eurder.domain.phonenumber;

import java.util.Objects;
import java.util.regex.Pattern;

public class PhoneNumber {
    private final CountryCode countryCode;
    private final String localNumber;

    public PhoneNumber(CountryCode countryCode, String localNumber) {
        this.countryCode = countryCode;
        this.localNumber = validateLocalNumber(localNumber);
    }

    public String validateLocalNumber(String localNumber) {
        String formatedLocalNumber = localNumber.startsWith("0") ? localNumber.trim().replace(" ", "").substring(1) : localNumber.trim().replace(" ", "");
        System.out.println(formatedLocalNumber);
        boolean isValidLocalNumber = Pattern.matches("[0-9]{8,9}", formatedLocalNumber);
        if (!isValidLocalNumber) {
            throw new IllegalArgumentException("The provided phone number is not valid");
        }
        if (formatedLocalNumber.length() == 8) {
            return String.format("%s %s %s %s", formatedLocalNumber.substring(0,2), formatedLocalNumber.substring(2,4), formatedLocalNumber.substring(4, 6), formatedLocalNumber.substring(6,8));
        }
        return String.format("%s %s %s %s", formatedLocalNumber.substring(0,3), formatedLocalNumber.substring(3,5), formatedLocalNumber.substring(5, 7), formatedLocalNumber.substring(7,9));
    }

    public String getFullPhoneNumberAsString() {
        return String.format("%s %s", countryCode, localNumber);
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
