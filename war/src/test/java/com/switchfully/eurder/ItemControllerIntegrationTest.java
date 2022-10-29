package com.switchfully.eurder;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.service.item.dto.CreateItemDTO;
import com.switchfully.eurder.service.item.dto.ItemDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTest {
    @LocalServerPort
    private int port;
    private final static String BASE_URI = "http://localhost";

    String adminBase64 = Base64.getEncoder().encodeToString("admin@eurder.com:password".getBytes());

    @Test
    void createCustomer_givenValidCustomer() {
        CreateItemDTO createItemDTO = new CreateItemDTO()
                .setName("name")
                .setDescription("description")
                .setPrice(1.1)
                .setStockCount(3);

        ItemDTO result = RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(port)
                .body(createItemDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .headers("Authorization", "Basic " + adminBase64)
                .post("items")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ItemDTO.class);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(createItemDTO.getName());
        assertThat(result.getDescription()).isEqualTo(createItemDTO.getDescription());
        assertThat(result.getPrice()).isEqualTo(new Price(createItemDTO.getPrice()).toString());
        assertThat(result.getStockCount()).isEqualTo(createItemDTO.getStockCount());
    }
}
