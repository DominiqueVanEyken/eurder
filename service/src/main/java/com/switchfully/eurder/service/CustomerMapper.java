package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.AddressBuilder;
import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.domain.CustomerBuilder;
import com.switchfully.eurder.service.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.dto.CustomerDTO;

public class CustomerMapper {
    public Customer mapDTOtoCustomer(CreateCustomerDTO createCustomerDTO) {
        return new CustomerBuilder()
                .setFirstname(createCustomerDTO.getFirstname())
                .setLastname(createCustomerDTO.getLastname())
                .setEmailAddress(createCustomerDTO.getEmailAddress())
                .setAddress(
                        new AddressBuilder()
                                .setStreetName(createCustomerDTO.getStreetName())
                                .setStreetNumber(createCustomerDTO.getStreetNumber())
                                .setPostalCode(createCustomerDTO.getPostalCode())
                                .setCityName(createCustomerDTO.getCityName())
                                .build()
                        )
                .setPhoneNumber(createCustomerDTO.getPhoneNumber())
                .build();
    }

    public CustomerDTO mapCustomerToDTO(Customer customer) {
        return new CustomerDTO()
                .setCustomerID(customer.getCustomerID())
                .setFirstname(customer.getFirstname())
                .setLastname(customer.getLastname())
                .setEmailAddress(customer.getEmailAddress())
                .setAddress(customer.getFullAddress())
                .setPhoneNumber(customer.getPhoneNumber());
    }
}
