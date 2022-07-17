package com.example.machallenge.models.dto;

import com.example.machallenge.models.entities.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapping;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NotNull(message = "La direccion no puede ser nula.")
    @NotBlank(message = "La direccion no puede estar vacia.")
    private String address;
    @NotNull(message = "El email no puede ser nulo.")
    @NotBlank(message = "El email no  puede estar vacio.")
    @Email(message = "Formato incorrecto de email.")
    private String mail;
    @NotNull(message = "El telefono no puede ser nulo.")
    @NotBlank(message = "El telefono no puede estar vacio.")
    private String phone;
    @NotNull(message = "El horario no puede ser nulo.")
    @FutureOrPresent(message = "No se puede definir un horario pasado.")
    private LocalTime scheduleTime;
    @NotEmpty(message = "El pedido debe tener al menos un detalle.")
    @NotNull(message = "El detalle no puede ser nulo.")
    private List<OrderDetailDTO> orderDetails;

}
