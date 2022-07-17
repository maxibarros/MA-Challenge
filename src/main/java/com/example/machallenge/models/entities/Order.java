package com.example.machallenge.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @Column(name = "direccion", nullable = false, length = 200)
    private String address;

    @Column(name = "email", nullable = false, length = 50)
    private String mail;

    @Column(name = "telefono", nullable = false, length = 25)
    private String phone;

    @Column(name = "horario", nullable = false)
    private LocalTime scheduleTime;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate createDate;

    @Column(name = "monto_total", nullable = false)
    private float amount;

    @Column(name = "aplico_descuento", nullable = false)
    private boolean discount;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

}
