package com.switchfully.eurder;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.address.PostalCode;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.phonenumber.CountryCode;
import com.switchfully.eurder.service.customer.CustomerMapper;
import com.switchfully.eurder.service.customer.CustomerService;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerControllerIntegrationTest {
    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;

    private final CreateCustomerDTO createCustomer = new CreateCustomerDTO()
            .setFirstname("createCustomer")
            .setLastname("dto")
            .setEmailAddress("user@test.be")
            .setStreetName("Street")
            .setStreetNumber("1")
            .setPostalCode("1111")
            .setCityName("city")
            .setCountryCode(CountryCode.BEL.toString())
            .setLocalNumber("123 45 67 89")
            .setPassword("password");
    private CustomerDTO customerDTO;

    @BeforeEach
    void setup() {
        customerDTO = customerService.createNewCustomer(createCustomer);
    }

    @Nested
    class gettingAllCustomers {
        @Test
        void gettingAllCustomers_givenValidAdmin() {
            String base64 = Base64.getEncoder().encodeToString("admin@eurder.com:admin@eurder".getBytes());
            CustomerDTO[] result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(CustomerDTO[].class);

            assertThat(result).isNotNull();
            assertThat(result.length).isGreaterThan(0);
        }

        @Test
        void gettingAllCustomers_givenNotAuthorized() {
            String base64 = Base64.getEncoder().encodeToString("user@test.be:password".getBytes());
            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.FORBIDDEN.value());
        }
    }

    @Nested
    class gettingCustomerByID {
        private final CustomerMapper customerMapper = new CustomerMapper();

        @Test
        void gettingCustomerByID_givenValidData() {
            String base64 = Base64.getEncoder().encodeToString("admin@eurder.com:admin@eurder".getBytes());
            CustomerDTO result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers/" + customerDTO.getCustomerID())
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(CustomerDTO.class);

            assertThat(result).isNotNull();
            assertThat(result.getCustomerID()).isNotNull();
            assertThat(result.getFirstname()).isEqualTo(customerDTO.getFirstname());
            assertThat(result.getLastname()).isEqualTo(customerDTO.getLastname());
            assertThat(result.getEmailAddress()).isEqualTo(customerDTO.getEmailAddress());
        }

        @Test
        void gettingCustomerByID_givenNotAuthorized() {
            String base64 = Base64.getEncoder().encodeToString("user@test.be:password".getBytes());
            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers/CID20221001")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.FORBIDDEN.value());
        }
    }

    @Nested
    class createCustomer {
        @Test
        void createCustomer_givenValidCustomer() {
            CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO()
                    .setFirstname("firstname")
                    .setLastname("lastname")
                    .setEmailAddress("createUser@test.be")
                    .setStreetName("streetname")
                    .setStreetNumber("1")
                    .setPostalCode("1111")
                    .setCityName("city")
                    .setCountryCode("+32")
                    .setLocalNumber("12 34 56 78")
                    .setPassword("password");

            Address address = new Address("streetname", "1", new PostalCode("1111", "city"));

            CustomerDTO result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .body(createCustomerDTO)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .post("customers")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .as(CustomerDTO.class);

            assertThat(result).isNotNull();
            assertThat(result.getCustomerID()).isNotNull();
            assertThat(result.getFirstname()).isEqualTo(createCustomerDTO.getFirstname());
            assertThat(result.getLastname()).isEqualTo(createCustomerDTO.getLastname());
            assertThat(result.getEmailAddress()).isEqualTo(createCustomerDTO.getEmailAddress());
            assertThat(result.getAddress()).isEqualTo(address.getFullAddressAsString());
            assertThat(result.getPhoneNumber()).isEqualTo("+32 12 34 56 78");
        }
    }


}
