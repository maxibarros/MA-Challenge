package com.example.machallenge.mappers.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @NotNull(message = "{order.address.notnull}")
    @NotBlank(message = "{order.address.notblank}")
    private String direccion;
    @NotNull(message = "{order.mail.notnull}")
    @NotBlank(message = "{order.mail.notblank}")
    @Email(message = "{order.mail.format}")
    private String email;
    @NotNull(message = "{order.phone.notnull}")
    @NotBlank(message = "{order.phone.notblank}")
    private String telefono;
    @NotNull(message = "{order.scheduleTime.notnull}")
    @NotBlank(message = "{order.scheduleTime.notblank}")
    private String horario;
    @NotEmpty(message = "{order.orderDetailList.notempty}")
    @NotNull(message = "{order.orderDetailList.notnull}")
    @Valid
    private List<OrderDetailRequestDTO> detalle;

}
