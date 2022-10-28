package com.switchfully.eurder.domain.phonenumber;

import java.util.NoSuchElementException;

public enum CountryCode {
    BEL("+32"),
    NED("+31");

    private final String countryCode;

    CountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public static CountryCode findCountryCode(String countryCodeToFind) {
        for (CountryCode countryCode : CountryCode.values()) {
            if (countryCode.countryCode.equals(countryCodeToFind)) {
                return countryCode;
            }
        }
        throw new NoSuchElementException("The provided country code could not be found");
    }

    @Override
    public String toString() {
        return countryCode;
    }
}
