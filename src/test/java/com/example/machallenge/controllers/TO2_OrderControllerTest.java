package com.example.machallenge.controllers;

import com.example.machallenge.mappers.pedidos.OrderMapper;
import com.example.machallenge.mappers.pedidos.dto.OrderDetailRequestDTO;
import com.example.machallenge.mappers.pedidos.dto.OrderDetailResponseDTO;
import com.example.machallenge.mappers.pedidos.dto.OrderRequestDTO;
import com.example.machallenge.mappers.pedidos.dto.OrderResponseDTO;
import com.example.machallenge.mappers.productos.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TO2_OrderControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    @Order(0)
    void saveProductsToOrder() throws Exception {
        val saveProductA = new ProductDTO("89efb206-2aa6-4e21-8a23-5765e3de1f31",
                "Jamon y morrones",
                "Pizza de jamon y morrones",
                "Mozzarella, jamon, morrones y aceitunas verdes",
                550D);
        val saveProductB = new ProductDTO("e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1",
                "Palmitos",
                "Pizza de palmitos",
                "Mozzarella, palmitos, jamon, salsa golf y aceitunas verdes",
                600D);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveProductA)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveProductB)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
    }

    @Test
    @Order(1)
    void saveOrderSuccesfull() throws Exception {
        val orderDetails = new ArrayList<OrderDetailRequestDTO>(Arrays.asList(
                new OrderDetailRequestDTO("89efb206-2aa6-4e21-8a23-5765e3de1f31", 1),
                new OrderDetailRequestDTO("e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1", 1)
        ));

        val orderRequestDTO = new OrderRequestDTO("Dorton Road 80",
                "tsayb@opera.com",
                "(0351) 48158101",
                "21:00",
                orderDetails);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("direccion").value(orderRequestDTO.getDireccion()))
                .andExpect(jsonPath("email").value(orderRequestDTO.getEmail()))
                .andExpect(jsonPath("telefono").value(orderRequestDTO.getTelefono()))
                .andExpect(jsonPath("detalle[0].producto").value(orderDetails.get(0).getProducto()))
                .andExpect(jsonPath("detalle[0].cantidad").value(orderDetails.get(0).getCantidad()))
                .andExpect(jsonPath("detalle[1].cantidad").value(orderDetails.get(1).getCantidad()))
                .andExpect(jsonPath("detalle[1].cantidad").value(orderDetails.get(1).getCantidad()))
                .andExpect(jsonPath("fecha").exists())
                .andExpect(jsonPath("total").exists())
                .andExpect(jsonPath("descuento").exists())
                .andExpect(jsonPath("estado").exists());
    }

    @Test
    @Order(2)
    void saveOrderSuccesfullWithDiscount() throws Exception {
        val orderDetailsWithDiscount = new ArrayList<OrderDetailRequestDTO>(Arrays.asList(
                new OrderDetailRequestDTO("89efb206-2aa6-4e21-8a23-5765e3de1f31", 3),
                new OrderDetailRequestDTO("e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1", 2)
        ));

        val orderRequestDTOWithDiscount = new OrderRequestDTO("Dorton Road 80",
                "tsayb@opera.com",
                "(0351) 48158101",
                "21:00",
                orderDetailsWithDiscount);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTOWithDiscount)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("direccion").value(orderRequestDTOWithDiscount.getDireccion()))
                .andExpect(jsonPath("email").value(orderRequestDTOWithDiscount.getEmail()))
                .andExpect(jsonPath("telefono").value(orderRequestDTOWithDiscount.getTelefono()))
                .andExpect(jsonPath("detalle[0].producto").value(orderDetailsWithDiscount.get(0).getProducto()))
                .andExpect(jsonPath("detalle[0].cantidad").value(orderDetailsWithDiscount.get(0).getCantidad()))
                .andExpect(jsonPath("detalle[1].cantidad").value(orderDetailsWithDiscount.get(1).getCantidad()))
                .andExpect(jsonPath("detalle[1].cantidad").value(orderDetailsWithDiscount.get(1).getCantidad()))
                .andExpect(jsonPath("fecha").exists())
                .andExpect(jsonPath("total").exists())
                .andExpect(jsonPath("descuento").exists())
                .andExpect(jsonPath("descuento").value(Boolean.TRUE))
                .andExpect(jsonPath("estado").exists())
                .andReturn();

        OrderResponseDTO orderResponseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                OrderResponseDTO.class);
        Double totalWithoutDiscount = orderResponseDTO.getDetalle()
                .stream()
                .mapToDouble(OrderDetailResponseDTO::getImporte).sum();

        Assertions.assertNotEquals(totalWithoutDiscount, orderResponseDTO.getTotal());
        Assertions.assertEquals(totalWithoutDiscount - (totalWithoutDiscount * 0.3), orderResponseDTO.getTotal());
        Assertions.assertTrue(totalWithoutDiscount > orderResponseDTO.getTotal());
    }

    @Test
    @Order(4)
    void saveOrderWithErrors() throws Exception {
        val orderDetailsWithErrors = new ArrayList<OrderDetailRequestDTO>(Arrays.asList(
                new OrderDetailRequestDTO("89efb206-2aa6-4e21-8a23-5765e3de1f31", 1),
                new OrderDetailRequestDTO("e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1", 1)
        ));

        val orderRequestDTOWithErrors = new OrderRequestDTO("Dorton Road 80",
                "tsayb@opera.com",
                "(0351) 48158101",
                "",
                orderDetailsWithErrors);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTOWithErrors)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("horario").value("El horario no puede estar vacio."));
    }


    @Test
    @Order(5)
    void findOrdersByDate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andReturn();

        List<OrderResponseDTO> orderResponseDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, OrderResponseDTO.class));
        Assertions.assertTrue(!orderResponseDTOList.isEmpty());
    }

}