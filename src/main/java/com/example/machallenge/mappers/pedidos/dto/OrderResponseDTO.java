package com.example.machallenge.mappers.pedidos.dto;

import com.example.machallenge.models.Order;
import com.example.machallenge.models.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private LocalDate fecha;
    private String direccion;
    private String email;
    private String telefono;
    private LocalTime horario;
    private List<OrderDetailResponseDTO> detalle;
    private Double total;
    private Boolean descuento;
    private String estado;

}