package com.switchfully.eurder;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.StockStatus;
import com.switchfully.eurder.service.item.dto.CreateItemDTO;
import com.switchfully.eurder.service.item.dto.ItemDTO;
import com.switchfully.eurder.service.item.dto.UpdateItemDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
        @Autowired
        ItemRepository itemRepository;

    private final String adminBase64 = Base64.getEncoder().encodeToString("admin@eurder.com:password".getBytes());

    @Nested
    class getAllItems {

        @Test
        void getAllItems_givenValidAuthorization() {
            ItemDTO[] result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .headers("Authorization", "Basic " + adminBase64)
                    .get("items")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(ItemDTO[].class);

            assertThat(result).isNotNull();
            assertThat(result.length).isEqualTo(itemRepository.getAllItemsFromRepository().size());
        }

        @Test
        void getAllItems_givenFilterForStockStatus() {
            ItemDTO[] result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .headers("Authorization", "Basic " + adminBase64)
                    .get("items?stockStatus=low")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(ItemDTO[].class);

            assertThat(result).isNotNull();
            assertThat(result.length).isEqualTo(2);
        }
    }

    @Nested
    class creatingItem {
        @Test
        void createItem_givenValidCustomerAndData() {
            CreateItemDTO createItemDTO = new CreateItemDTO().setName("name").setDescription("description").setPrice(1.1).setStockCount(3);

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
            assertThat(result.getStockStatus()).isEqualTo(StockStatus.STOCK_LOW.toString());
        }

        @Test
        void createItem_givenInvalidData() {
            CreateItemDTO createItemDTO = new CreateItemDTO().setName(null).setDescription("description").setPrice(1.1).setStockCount(3);

            RestAssured
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
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class updateItem {
        @Test
        void updateItem_givenValidData() {
            String itemID = "IID20221001";
            UpdateItemDTO updateItemDTO = new UpdateItemDTO().setName("update").setDescription("description").setPrice(1.1).setStockCount(3);

            ItemDTO result = RestAssured
                    .given()
                    .baseUri(BASE_URI)
                    .port(port)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .headers("Authorization", "Basic " + adminBase64)
                    .body(updateItemDTO)
                    .when()
                    .put("items/"+itemID)
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.ACCEPTED.value())
                    .extract()
                    .as(ItemDTO.class);

            assertThat(result).isNotNull();
            assertThat(result.getItemID()).isEqualTo(itemID);
            assertThat(result.getName()).isEqualTo(updateItemDTO.getName());
            assertThat(result.getDescription()).isEqualTo(updateItemDTO.getDescription());
            assertThat(result.getPrice()).isEqualTo(new Price(updateItemDTO.getPrice()).toString());
            assertThat(result.getStockStatus()).isEqualTo(StockStatus.STOCK_LOW.toString());
        }
    }
}
