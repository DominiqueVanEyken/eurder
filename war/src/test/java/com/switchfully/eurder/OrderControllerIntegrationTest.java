package com.switchfully.eurder;

import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;

import com.switchfully.eurder.service.order.dto.CreateItemGroupDTO;
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
public class OrderControllerIntegrationTest {
    public static final String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;
    private final String itemID = "IID20221001";
    private final String customerID = "CID20221002";
    private final List<CreateItemGroupDTO> createItemGroupDTOS = List.of(new CreateItemGroupDTO()
            .setItemID(itemID)
            .setAmount(2));

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
                    .post("customers/" + customerID + "/orders/order")
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
                    .post("customers/" + customerID + "/orders/order")
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
                    .post("customers/" + customerID + "/orders/order")
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
                    .post("customers/invalidCustomerID/orders/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class reoderAnOrder {
        @Autowired
        private OrderRepository orderRepository;

        @Test
        void reOrderingAnOrder_givenValidData() {
            Order order = orderRepository.getOrders().stream().toList().get(0);
            Customer customer = customerRepository.findCustomerByID(order.getCustomerID()).get();
            String customerBase64 = Base64.getEncoder().encodeToString((customer.getEmailAddress() + ":password").getBytes());

            OrderDTO result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + customerBase64)
                    .when()
                    .post("customers/" + order.getCustomerID() + "/orders/" + order.getOrderID() + "/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .as(OrderDTO.class);

            assertThat(result).isNotNull();
            assertThat(result.getOrderID()).isNotNull();
            assertThat(result.getOrderDate()).isEqualTo(LocalDate.now());
            assertThat(result.getCustomerID()).isEqualTo(customer.getCustomerID());
            assertThat(result.getTotalPrice()).isNotNull();
            assertThat(result.getOrderList().size()).isEqualTo(order.getOrderList().size());
        }

        @Test
        void reOrderingAnOrder_givenInvalidCustomerData() {
            Order order = orderRepository.getOrders().stream().toList().get(0);
            Customer customer = customerRepository.findCustomerByID(order.getCustomerID()).get();
            String customerBase64 = Base64.getEncoder().encodeToString((customer.getEmailAddress() + ":password").getBytes());

            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + customerBase64)
                    .when()
                    .post("customers/invalidID/orders/" + order.getOrderID() + "/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.FORBIDDEN.value());
        }

        @Test
        void reOrderingAnOrder_givenInvalidOrderID() {
            Order order = orderRepository.getOrders().stream().toList().get(0);
            Customer customer = customerRepository.findCustomerByID(order.getCustomerID()).get();
            String customerBase64 = Base64.getEncoder().encodeToString((customer.getEmailAddress() + ":password").getBytes());

            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + customerBase64)
                    .when()
                    .post("customers/" + order.getCustomerID() + "/orders/invalidID/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}