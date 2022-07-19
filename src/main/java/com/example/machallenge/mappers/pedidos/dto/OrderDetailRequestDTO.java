package com.example.machallenge.mappers.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequestDTO {

    @NotNull(message = "{orderDetail.product.notnull}")
    private String producto;
    @NotNull(message = "{orderDetail.quantity.notnull}")
    @Positive(message = "{orderDetail.quantity.positive}")
    private Integer cantidad;
}
