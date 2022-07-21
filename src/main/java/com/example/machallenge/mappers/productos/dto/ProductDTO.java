package com.example.machallenge.mappers.productos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotNull(message = "{product.productID.notnull}")
    @NotBlank(message = "{product.productID.notblank}")
    private String id;
    @NotNull(message = "{product.name.notnull}")
    @NotBlank(message = "{product.name.notblank}")
    private String nombre;
    @NotNull(message = "{product.shortDescription.notnull}")
    @NotBlank(message = "{product.shortDescription.notblank}")
    private String descripcionCorta;
    @NotNull(message = "{product.longDescription.notnull}")
    @NotBlank(message = "{product.longDescription.notblank}")
    private String descripcionLarga;
    @NotNull(message = "{product.unitPrice.notnull}")
    @Positive(message = "{product.unitPrice.positive}")
    private Double precioUnitario;

}
