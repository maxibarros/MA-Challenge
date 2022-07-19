package com.example.machallenge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orderDetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends BaseEntity {

    @ManyToOne()
    @JoinColumn(name="pedido_cabecera_id", nullable = false)
    private Order order;

    @ManyToOne()
    @JoinColumn(name="producto_id", nullable = false)
    private Product product;

    @Column(name = "cantidad", nullable = false)
    private Integer quantity;

    @Column(name = "precio_unitario", nullable = false)
    private Double detailPrice;

    public Double calculateDetailPrice() {
        if(product != null) {
            return product.getUnitPrice() * quantity;
        } return -1D;
    }
}
