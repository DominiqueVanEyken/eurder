package com.switchfully.eurder;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.address.PostalCode;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.customer.Role;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.itemgroup.ItemGroupRepository;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;

import com.switchfully.eurder.domain.phonenumber.CountryCode;
import com.switchfully.eurder.domain.phonenumber.PhoneNumber;
import com.switchfully.eurder.service.order.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderControllerIntegrationTest {
    private static final String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ItemGroupRepository itemGroupRepository;
    private final Customer customer = new Customer("firstname", "lastname", "user@test.be", new Address("street", "1", new PostalCode("1111", "consumer valley")), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);
    private final Item item1 = new Item("name1", "description", new Price(1.0), 10);
    private final Item item2 = new Item("name2", "description", new Price(2.0), 5);
    private final Order order = new Order(customer.getCustomerID());
    private final ItemGroup itemGroup1 = new ItemGroup(order, item1, item1.getName(), 1, item1.getShippingDateForAmount(1), item1.getPrice());

    @Nested
    class orderItems {
        CreateItemGroupDTO createItemGroupDTO1 = new CreateItemGroupDTO()
                .setItemID(1001)
                .setAmount(2);
        CreateItemGroupDTO createItemGroupDTO2 = new CreateItemGroupDTO()
                .setItemID(1002)
                .setAmount(2);
        List<CreateItemGroupDTO> createItemGroupDTOS = List.of(createItemGroupDTO1, createItemGroupDTO2);

        @Test
        void orderItems_givenValidDataAndAuthorization() {
            customerRepository.save(customer);
            itemRepository.save(item1);
            itemRepository.save(item2);
            String customerBase64 = Base64.getEncoder().encodeToString("user@test.be:password".getBytes());
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
                    .post("customers/" + customer.getCustomerID() + "/orders/order")
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
                    .post("customers/" + customer.getCustomerID() + "/orders/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void orderItems_givenInvalidPassword() {
            CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                    .setOrderList(createItemGroupDTOS);
            String authorization = Base64.getEncoder().encodeToString("user@test.be:invalid".getBytes());

            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .body(createOrderDTO)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + authorization)
                    .when()
                    .post("customers/" + customer.getCustomerID() + "/orders/order")
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

        @Test
        void reOrderingAnOrder_givenValidData() {
            customerRepository.save(customer);
            itemRepository.save(item1);
            itemRepository.save(item2);
            itemGroupRepository.save(itemGroup1);
            order.updatePrice(List.of(itemGroup1));
            orderRepository.save(order);
            Order reorder = orderRepository.findById(order.getOrderID()).get();
            Customer customer = customerRepository.findById(OrderControllerIntegrationTest.this.customer.getCustomerID()).get();
            String customerBase64 = Base64.getEncoder().encodeToString((customer.getEmailAddress() + ":password").getBytes());

            OrderDTO result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + customerBase64)
                    .when()
                    .post("customers/" + reorder.getCustomerID() + "/orders/" + reorder.getOrderID() + "/order")
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
            assertThat(result.getOrderList().size()).isNotNull();
        }

        @Test
        void reOrderingAnOrder_givenInvalidCustomerData() {
            Customer invalid = new Customer("firstname", "lastname", "invalid@test.be", new Address("street", "1", new PostalCode("1111", "consumer valley")), new PhoneNumber(CountryCode.BEL, "123 45 67 89"), "password", Role.CUSTOMER);
            customerRepository.save(customer);
            customerRepository.save(invalid);

            String customerBase64 = Base64.getEncoder().encodeToString((invalid.getEmailAddress() + ":password").getBytes());

            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + customerBase64)
                    .when()
                    .post("customers/" + order.getCustomerID() + "/orders/" + order.getOrderID() + "/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.FORBIDDEN.value());
        }

        @Test
        void reOrderingAnOrder_givenInvalidOrderID() {
            customerRepository.save(customer);

            String customerBase64 = Base64.getEncoder().encodeToString((customer.getEmailAddress() + ":password").getBytes());

            RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + customerBase64)
                    .when()
                    .post("customers/" + customer.getCustomerID() + "/orders/invalidID/order")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
