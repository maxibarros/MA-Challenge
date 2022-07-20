package com.example.machallenge;

import com.example.machallenge.mappers.productos.dto.ProductDTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MaChallengeApplicationTests {

    private static RequestSpecification spec;
    @BeforeAll
    public static void initSpec(){
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:8080/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    @Order(1)
    public void createProductoRest() {

        ProductDTO producto = new ProductDTO("89efb206-2aa6-4e21-8a23-5765e3de1f312",
                "Especial",
                "Pizza de jamón y morrones",
                "Mozzarella, jamón, morrones y aceitunas verdes",
                500D);
        given().spec(spec)
                .body(producto)
                .when()
                .post("productos")
                .then()
                .statusCode(201);
    }

}
