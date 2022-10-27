package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.address.AddressBuilder;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerBuilder;
import com.switchfully.eurder.domain.customer.Role;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;

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
                .setPassword(createCustomerDTO.getPassword())
                .setRole(Role.CUSTOMER)
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
