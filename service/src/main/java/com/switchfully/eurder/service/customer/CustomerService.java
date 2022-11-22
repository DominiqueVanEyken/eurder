package com.switchfully.eurder.service.customer;

import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.exceptions.UnauthorizedException;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = new CustomerMapper();
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerMapper.mapCustomerToDTO(customerRepository.findAll());
    }

    public CustomerDTO createNewCustomer(CreateCustomerDTO createCustomerDTO) {
        Customer customer = customerMapper.mapDTOtoCustomer(createCustomerDTO);
        if (isCustomerUnique(customer)) {
            customerRepository.save(customer);
            log.info("Saving customer with ID " + customer.getCustomerID());
            return customerMapper.mapCustomerToDTO(customer);
        }
        throw new IllegalArgumentException("Customer already exists");
    }

    public CustomerDTO getCustomerByID(String customerID) {
        Optional<Customer> customer = customerRepository.findById(customerID);
        if (customer.isPresent()) {
            return customerMapper.mapCustomerToDTO(customer.get());
        }
        throw new NoSuchElementException("Customer with ID " + customerID + " does not exist");
    }

    public void validateIfCustomerIDBelongsToUsername(String customerID, String userName) {
        Customer customer = customerRepository.findById(customerID).orElseThrow(() -> new NoSuchElementException("Customer with ID " + customerID + " does not exist"));
        if (!customer.getEmailAddress().equals(userName)) {
            throw new UnauthorizedException();
        }
    }

    private boolean isCustomerUnique(Customer customerToVerify) {
        Collection<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            if (customer.getEmailAddress().equals(customerToVerify.getEmailAddress())) {
                return false;
            }
        }
        return true;
    }
}
