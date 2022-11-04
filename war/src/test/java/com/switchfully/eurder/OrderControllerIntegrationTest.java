package com.switchfully.eurder;

import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.report.dto.ShippingReportDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTest {
    public static final String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;
    @Autowired
    OrderRepository orderRepository;
    private final String adminBase64 = Base64.getEncoder().encodeToString("admin@eurder.com:password".getBytes());

    @Test
    void getShippingReportForToday() {
        ShippingReportDTO result = RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(port)
                .headers("Authorization", "Basic " + adminBase64)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("orders/shipping")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ShippingReportDTO.class);

        assertThat(result).isNotNull();
        assertThat(result.getShippingDate()).isEqualTo(LocalDate.now());
        assertThat(result.getItemGroups()).isNotNull();
    }

    @Test
    void getShippingReportForToday_givenInvalidUsername() {
        String unauthorizedUserBase64 = Base64.getEncoder().encodeToString("user1@test.be:password".getBytes());
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(port)
                .headers("Authorization", "Basic " + unauthorizedUserBase64)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("orders/shipping")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
