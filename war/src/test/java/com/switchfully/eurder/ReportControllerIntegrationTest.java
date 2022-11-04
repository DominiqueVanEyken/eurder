package com.switchfully.eurder;

import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.order.OrderMapper;
import com.switchfully.eurder.service.report.ReportMapper;
import com.switchfully.eurder.service.report.dto.ReportDTO;
import com.switchfully.eurder.service.report.dto.ShippingReportDTO;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportControllerIntegrationTest {
    public static final String BASE_URI = "http://localhost";
    private final ReportMapper reportMapper = new ReportMapper();
    @LocalServerPort
    private int port;
    @Autowired
    OrderRepository orderRepository;
    private final String adminBase64 = Base64.getEncoder().encodeToString("admin@eurder.com:password".getBytes());

    @Nested
    class gettingShippingToday {
        @Test
        void getShippingReportForToday () {
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
        void getShippingReportForToday_givenInvalidUsername () {
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

    @Nested
    class requestingReport {
        private final String customerID = "CID20221002";
        @Autowired
        OrderRepository orderRepository;
        OrderMapper orderMapper = new OrderMapper();
        @Test
        void requestingReport_givenValidData() {
            String base64 = Base64.getEncoder().encodeToString("user1@test.be:password".getBytes());
            ReportDTO expected = reportMapper.mapOrdersToReportDTO(orderRepository.getOrdersByCustomerID(customerID));
            ReportDTO result  = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers/" + customerID +"/orders/report")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(ReportDTO.class);

            assertThat(result.getOrderReports().size()).isEqualTo(expected.getOrderReports().size());
            assertThat(result.getTotalPrice()).isEqualTo(expected.getTotalPrice());
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
                    .get("customers/" + customerID +"/orders/report")
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
                    .get("customers/invalidCustomerID/orders/report")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
