package com.example.machallenge.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPutRequestDTO {

    @NotNull(message = "El nombre del producto no puede ser nulo.")
    @NotBlank(message = "El nombre del producto no puede estar vacio.")
    private String name;
    @NotNull(message = "La descripcion corta no puede ser nula.")
    @NotBlank(message = "La descripcion corta no puede estar vacia.")
    private String shortDescription;
    @NotNull(message = "La descripcion larga no puede ser nula.")
    @NotBlank(message = "La descripcion larga no puede estar vacia.")
    private String longDescription;
    @NotNull(message = "El precio unitario no puede ser nulo.")
    @Positive(message = "El precio unitario no puede ser menor o igual a cero.")
    private float unitPrice;


}
