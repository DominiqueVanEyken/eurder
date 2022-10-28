package com.switchfully.eurder;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.service.customer.dto.CreateCustomerDTO;
import com.switchfully.eurder.service.customer.dto.CustomerDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

    @LocalServerPort
    private int port;
    private final static String BASE_URI = "http://localhost";

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
