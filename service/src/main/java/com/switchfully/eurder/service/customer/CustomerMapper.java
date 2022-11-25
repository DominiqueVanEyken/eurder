package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.address.AddressBuilder;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerBuilder;
import com.switchfully.eurder.domain.customer.Role;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;
import com.switchfully.eurder.service.address.AddressMapper;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;

import java.util.Collection;
import java.util.List;

public class CustomerMapper {
    private final AddressMapper addressMapper = new AddressMapper();
    public Customer mapDTOtoCustomer(CreateCustomerDTO createCustomerDTO) {
        return new CustomerBuilder()
                .setFirstname(createCustomerDTO.getFirstname())
                .setLastname(createCustomerDTO.getLastname())
                .setEmailAddress(createCustomerDTO.getEmailAddress())
                .setAddress(addressMapper.mapDTOToAddress(createCustomerDTO.getAddress()))
                .setPhoneNumber(new PhoneNumber(createCustomerDTO.getCountryCode(), createCustomerDTO.getLocalNumber()))
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
                .setAddress(addressMapper.mapAddressToDTO(customer.getAddress()))
                .setPhoneNumber(customer.getPhoneNumber());
    }

    public List<CustomerDTO> mapCustomerToDTO(Collection<Customer> customers) {
        return customers.stream()
                .map(this::mapCustomerToDTO)
                .toList();
    }
}
