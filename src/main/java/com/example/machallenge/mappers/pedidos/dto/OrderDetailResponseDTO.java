package com.example.machallenge.mappers.pedidos.dto;

import com.example.machallenge.models.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponseDTO {

    private String producto;
    private String nombre;
    private Integer cantidad;
    private Double importe;

}
