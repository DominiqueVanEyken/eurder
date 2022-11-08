package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = new CustomerMapper();
    }

    public CustomerDTO createNewCustomer(CreateCustomerDTO createCustomerDTO) {
        Customer customer = customerMapper.mapDTOtoCustomer(createCustomerDTO);
        if (isCustomerUnique(customer)) {
            customerRepository.addCustomer(customer);
            return customerMapper.mapCustomerToDTO(customer);
        }
        throw new IllegalArgumentException("Customer already exists");
    }

    public CustomerDTO getCustomerByID(String customerID) {
        return customerMapper.mapCustomerToDTO(customerRepository.findCustomerByID(customerID));
    }

    public void validateIfCustomerIDExists(String customerID) {
        customerRepository.findCustomerByID(customerID);
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerMapper.mapCustomerToDTO(customerRepository.getAllCustomers());
    }

    public boolean isCustomerUnique(Customer customerToVerify) {
        Collection<Customer> customers = customerRepository.getAllCustomers();
        for (Customer customer : customers) {
            if (customer.getEmailAddress().equals(customerToVerify.getEmailAddress())) {
                return false;
            }
        }
        return true;
    }
}
