package com.switchfully.eurder;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.address.PostalCode;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerBuilder;
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
import com.switchfully.eurder.service.order.OrderMapper;
import com.switchfully.eurder.service.report.ReportMapper;
import com.switchfully.eurder.service.report.dto.ItemGroupReportDTO;
import com.switchfully.eurder.service.report.dto.ReportDTO;
import com.switchfully.eurder.service.report.dto.ShippingReportDTO;
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

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReportControllerIntegrationTest {
    public static final String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ItemGroupRepository itemGroupRepository;
    private final ReportMapper reportMapper = new ReportMapper();
    private final String adminBase64 = Base64.getEncoder().encodeToString("admin@eurder.com:admin@eurder".getBytes());
    private final Customer customer = new CustomerBuilder()
            .setFirstname("firstname")
            .setLastname("lastname")
            .setEmailAddress("user@test.com")
            .setAddress(new Address("streetName", "1", new PostalCode("1111", "consumer valley")))
            .setPhoneNumber(new PhoneNumber(CountryCode.BEL, "012 34 56 78"))
            .setPassword("password")
            .setRole(Role.CUSTOMER)
            .build();
    private final Item item1 = new Item("name1", "description", new Price(1.0), 10);
    private final Item item2 = new Item("name2", "description", new Price(2.0), 5);
    private final Order order = new Order(customer.getCustomerID());
    private final ItemGroup itemGroup1 = new ItemGroup(order, item1, 1);

    @Nested
    class gettingShippingToday {

        @Test
        void getShippingReportForToday() {
            itemRepository.save(item1);
            itemRepository.save(item2);
            customerRepository.save(customer);
            itemGroupRepository.save(itemGroup1);
            order.updatePrice(itemGroup1.getTotalPrice());
            orderRepository.save(order);

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
            customerRepository.save(customer);
            String unauthorizedUserBase64 = Base64.getEncoder().encodeToString("user@test.com:password".getBytes());
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
        @Test
        void requestingReport_givenValidData() {
            itemRepository.save(item1);
            itemRepository.save(item2);
            customerRepository.save(customer);
            itemGroupRepository.save(itemGroup1);
            order.updatePrice(itemGroup1.getTotalPrice());
            orderRepository.save(order);
            String base64 = Base64.getEncoder().encodeToString("user@test.com:password".getBytes());
            List<ItemGroupReportDTO> itemGroupReportDTO = reportMapper.mapItemGroupToItemGroupReportDTO(List.of(itemGroup1));
            ReportDTO expected = new ReportDTO()
                    .setOrderReports(List.of(reportMapper.mapOrderToOrderReportDTO(order, itemGroupReportDTO)))
                    .setTotalPrice(order.getTotalPrice().toString());
            ReportDTO result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .headers("Authorization", "Basic " + base64)
                    .when()
                    .get("customers/" + order.getCustomerID() + "/orders/report")
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
                    .get("customers/" + order.getCustomerID() + "/orders/report")
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
