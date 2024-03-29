package com.example.machallenge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {


    @Column(name = "producto_ID", nullable = false, unique = true, updatable = false, length = 250)
    private String productID;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "descripcion_corta", nullable = false, length = 100)
    private String shortDescription;

    @Column(name = "descripcion_larga", length = 250)
    private String longDescription;

    @Column(name = "precio_unitario", nullable = false)
    private Double unitPrice;

}
