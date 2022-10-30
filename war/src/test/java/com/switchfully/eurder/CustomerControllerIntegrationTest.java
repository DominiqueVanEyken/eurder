package com.switchfully.eurder;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.service.customer.CustomerMapper;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;
import com.switchfully.eurder.service.order.dto.itemgroup.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    CustomerRepository customerRepository;
    private final CustomerMapper customerMapper = new CustomerMapper();
    private final static String BASE_URI = "http://localhost";
    private final String itemID = "IID20221001";
    private final String customerID = "CID20221002";

    private final List<CreateItemGroupDTO> createItemGroupDTOS = List.of(new CreateItemGroupDTO()
            .setItemID(itemID)
            .setAmount(2));

    @Nested
    class gettingAllCustomers {
        @Test
        void gettingAllCustomers_givenValidAdmin() {
            String base64 = Base64.getEncoder().encodeToString("admin@eurder.com:password".getBytes());
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
            String base64 = Base64.getEncoder().encodeToString("user1@test.be:password".getBytes());
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
        @Test
        void gettingCustomerByID_givenValidData() {
            String base64 = Base64.getEncoder().encodeToString("admin@eurder.com:password".getBytes());
            CustomerDTO expected = customerMapper.mapCustomerToDTO(customerRepository.getAllCustomers().stream().toList().get(0));
            CustomerDTO result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers/" + customerID)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(CustomerDTO.class);

            assertThat(result).isNotNull();
            assertThat(result.getCustomerID()).isEqualTo(expected.getCustomerID());
        }

        @Test
        void gettingCustomerByID_givenNotAuthorized() {
            String base64 = Base64.getEncoder().encodeToString("user1@test.be:password".getBytes());
            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers/" + customerID)
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
                    .setEmailAddress("user@test.be")
                    .setStreetName("streetname")
                    .setStreetNumber("1")
                    .setPostalCode("1111")
                    .setCityName("city")
                    .setCountryCode("+32")
                    .setLocalNumber("12 34 56 78")
                    .setPassword("password");

            Address address = new Address("streetname", "1", "1111", "city");

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

    @Nested
    class orderItems {
        @Test
        void orderItems_givenValidDataAndAuthorization() {
            String customerBase64 = Base64.getEncoder().encodeToString("user1@test.be:password".getBytes());
            CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                    .setOrderList(createItemGroupDTOS);

            OrderDTO result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .body(createOrderDTO)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + customerBase64)
                    .when()
                    .post("customers/" + customerID + "/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .as(OrderDTO.class);

            assertThat(result).isNotNull();
            assertThat(result.getOrderID()).isNotNull();
            assertThat(result.getOrderDate()).isEqualTo(LocalDate.now());
            assertThat(result.getCustomerID()).isEqualTo(customerID);
            assertThat(result.getTotalPrice()).isNotNull();
            assertThat(result.getOrderList()).isNotNull();
        }

        @Test
        void orderItems_givenInvalidUsername() {
            CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                    .setOrderList(createItemGroupDTOS);
            String authorization = Base64.getEncoder().encodeToString("invalid@test.be:password".getBytes());

            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .body(createOrderDTO)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + authorization)
                    .when()
                    .post("customers/" + customerID + "/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void orderItems_givenInvalidPassword() {
            CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                    .setOrderList(createItemGroupDTOS);
            String authorization = Base64.getEncoder().encodeToString("user1@test.be:invalid".getBytes());

            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .body(createOrderDTO)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + authorization)
                    .when()
                    .post("customers/" + customerID + "/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void orderItems_givenInvalidCustomerID() {
            CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                    .setOrderList(createItemGroupDTOS);
            String authorization = Base64.getEncoder().encodeToString("user1@test.be:password".getBytes());

            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .body(createOrderDTO)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + authorization)
                    .when()
                    .post("customers/invalidCustomerID/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class requestingReport {
        @Test
        void requestingReport_givenValidData() {
            String base64 = Base64.getEncoder().encodeToString("user1@test.be:password".getBytes());
            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers/" + customerID +"/report")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void requestingReport_givenInvalidData() {
            String base64 = Base64.getEncoder().encodeToString("invalid:password".getBytes());
            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers/" + customerID +"/report")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void requestingReport_givenInvalidCustomerID() {
            String base64 = Base64.getEncoder().encodeToString("user1@test.be:password".getBytes());
            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers/invalidCustomerID/report")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
