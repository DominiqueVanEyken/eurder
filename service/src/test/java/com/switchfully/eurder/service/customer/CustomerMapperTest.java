package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.Role;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    private final CustomerMapper customerMapper = new CustomerMapper();

    private final String firstname = "firstname";
    private final String lastname = "lastname";
    private final String email = "user@test.be";
    private final String streetName = "streetName";
    private final String streetNumber = "1";
    private final String postalCode = "1111";
    private final String city = "city";
    private final Address address = new Address(streetName, streetNumber, postalCode, city);
    private final String countryCode = "+32";
    private final String localNumber = "12 34 56 78";
    private final PhoneNumber phoneNumber = new PhoneNumber(countryCode, localNumber);
    private final String password = "password";

    @Test
    void creatingCreateCustomerDTO() {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                .setFirstname(firstname)
                .setLastname(lastname)
                .setEmailAddress(email)
                .setStreetName(streetName)
                .setStreetNumber(streetNumber)
                .setPostalCode(postalCode)
                .setCityName(city)
                .setCountryCode(countryCode)
                .setLocalNumber(localNumber)
                .setPassword(password);

        assertThat(createCustomerDTO.getFirstname()).isEqualTo(firstname);
        assertThat(createCustomerDTO.getLastname()).isEqualTo(lastname);
        assertThat(createCustomerDTO.getEmailAddress()).isEqualTo(email);
        assertThat(createCustomerDTO.getStreetName()).isEqualTo(streetName);
        assertThat(createCustomerDTO.getStreetNumber()).isEqualTo(streetNumber);
        assertThat(createCustomerDTO.getPostalCode()).isEqualTo(postalCode);
        assertThat(createCustomerDTO.getCityName()).isEqualTo(city);
        assertThat(createCustomerDTO.getLocalNumber()).isEqualTo(localNumber);
        assertThat(createCustomerDTO.getPassword()).isEqualTo(password);
    }

    @Test
    void creatingCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO()
                .setFirstname(firstname)
                .setLastname(lastname)
                .setEmailAddress(email)
                .setAddress(address.getFullAddressAsString())
                .setPhoneNumber(phoneNumber.getFullPhoneNumberAsString());

        assertThat(customerDTO.getFirstname()).isEqualTo(firstname);
        assertThat(customerDTO.getLastname()).isEqualTo(lastname);
        assertThat(customerDTO.getEmailAddress()).isEqualTo(email);
        assertThat(customerDTO.getAddress()).isEqualTo(address.getFullAddressAsString());
        assertThat(customerDTO.getPhoneNumber()).isEqualTo(phoneNumber.getFullPhoneNumberAsString());
    }

    @Test
    void mappingCustomerToDTO() {
        Customer customer = new Customer(firstname, lastname, email, address, phoneNumber, password, Role.CUSTOMER);

        CustomerDTO customerDTO = customerMapper.mapCustomerToDTO(customer);

        assertThat(customerDTO.getFirstname()).isEqualTo(firstname);
        assertThat(customerDTO.getLastname()).isEqualTo(lastname);
        assertThat(customerDTO.getEmailAddress()).isEqualTo(email);
        assertThat(customerDTO.getAddress()).isEqualTo(address.getFullAddressAsString());
        assertThat(customerDTO.getPhoneNumber()).isEqualTo(phoneNumber.getFullPhoneNumberAsString());
    }

    @Test
    void mappingDTOToCustomer() {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                .setFirstname(firstname)
                .setLastname(lastname)
                .setEmailAddress(email)
                .setStreetName(streetName)
                .setStreetNumber(streetNumber)
                .setPostalCode(postalCode)
                .setCityName(city)
                .setCountryCode(countryCode)
                .setLocalNumber(localNumber)
                .setPassword(password);

        Customer customer = customerMapper.mapDTOtoCustomer(createCustomerDTO);

        assertThat(customer.getFirstname()).isEqualTo(firstname);
        assertThat(customer.getLastname()).isEqualTo(lastname);
        assertThat(customer.getEmailAddress()).isEqualTo(email);
        assertThat(customer.getFullAddress()).isEqualTo(address.getFullAddressAsString());
        assertThat(customer.getPhoneNumber()).isEqualTo(phoneNumber.getFullPhoneNumberAsString());
        assertThat(customer.doesPasswordMatch(password)).isTrue();
        assertThat(customer.getRole()).isEqualTo(Role.CUSTOMER);
    }

}