package com.example.machallenge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "order_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    private static final int DISCOUNTQUANTITY = 3;
    private static final int DISCOUNTAMOUNT = 30;

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
    private Double amount;

    @Column(name = "aplico_descuento", nullable = false)
    private Boolean discount;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderState state;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    public boolean applyToDiscount() {
        if(!CollectionUtils.isEmpty(orderDetails)){
          Integer sumQuantity = orderDetails.stream()
                  .map(OrderDetail::getQuantity)
                  .collect(Collectors.summingInt(Integer::intValue));
          return sumQuantity >= DISCOUNTQUANTITY;
        } return false;
    }

    public Double calculateTotalAmount() {
        if(!CollectionUtils.isEmpty(orderDetails)){
            return orderDetails.stream()
                    .map(OrderDetail::calculateDetailPrice)
                    .collect(Collectors.summingDouble(Double::doubleValue));
        } return -1D;
    }

    public Double calculateTotalAmountWithDiscount() {
        Double amount = this.calculateTotalAmount();
        return amount - (amount*DISCOUNTAMOUNT/100);
    }

}
