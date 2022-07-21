package com.example.machallenge.controllers;

import com.example.machallenge.mappers.productos.dto.ProductDTO;
import com.example.machallenge.mappers.productos.dto.ProductPutRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TO1_ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    void saveProduct() throws Exception {
        val saveProduct = new ProductDTO("89efb206-2aa6-4e21-8a23-5765e3de1f31",
                "Especial",
                "Pizza de jamon y morrones",
                "Mozzarella, jamon, morrones y aceitunas verdes",
                500D);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveProduct)))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
    }

    @Test
    @Order(2)
    void getProduct() throws Exception {
        val getProduct = new ProductDTO("89efb206-2aa6-4e21-8a23-5765e3de1f31",
                "Especial",
                "Pizza de jamon y morrones",
                "Mozzarella, jamon, morrones y aceitunas verdes",
                500D);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .param("productID", getProduct.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("[0].id").value(getProduct.getId()))
                .andExpect(jsonPath("[0].nombre").value(getProduct.getNombre()))
                .andExpect(jsonPath("[0].descripcionCorta").value(getProduct.getDescripcionCorta()))
                .andExpect(jsonPath("[0].descripcionLarga").value(getProduct.getDescripcionLarga()))
                .andExpect(jsonPath("[0].precioUnitario").value(getProduct.getPrecioUnitario()));
    }

    @Test
    @Order(3)
    void getProductNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/productos/89efb206-2aa6-4e21-8a23-5765e3de1f30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("error").exists())
                .andExpect(jsonPath("error").value("Producto no encontrado."));
    }

    @Test
    @Order(4)
    void updateProduct() throws Exception {
        val putProduct = new ProductPutRequestDTO("Jamon y morrones",
                "Pizza de jamon y morrones",
                "Mozzarella, jamon, morrones y aceitunas verdes",
                500D);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/productos/89efb206-2aa6-4e21-8a23-5765e3de1f31")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putProduct)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productID", "89efb206-2aa6-4e21-8a23-5765e3de1f31"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("[0].id").value("89efb206-2aa6-4e21-8a23-5765e3de1f31"))
                .andExpect(jsonPath("[0].nombre").value(putProduct.getNombre()))
                .andExpect(jsonPath("[0].descripcionCorta").value(putProduct.getDescripcionCorta()))
                .andExpect(jsonPath("[0].descripcionLarga").value(putProduct.getDescripcionLarga()))
                .andExpect(jsonPath("[0].precioUnitario").value(putProduct.getPrecioUnitario()));
    }

    @Test
    @Order(5)
    void deleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/productos/89efb206-2aa6-4e21-8a23-5765e3de1f31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    @Order(6)
    void checkNoProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/productos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()))
                .andExpect(jsonPath("message").value("No se encontraron productos."));
    }
}