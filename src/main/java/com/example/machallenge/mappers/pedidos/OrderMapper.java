package com.example.machallenge.mappers.pedidos;

import com.example.machallenge.mappers.pedidos.dto.OrderDetailRequestDTO;
import com.example.machallenge.mappers.pedidos.dto.OrderDetailResponseDTO;
import com.example.machallenge.mappers.pedidos.dto.OrderRequestDTO;
import com.example.machallenge.mappers.pedidos.dto.OrderResponseDTO;
import com.example.machallenge.models.Order;
import com.example.machallenge.models.OrderDetail;
import com.example.machallenge.models.Product;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public Order mapOrderRequest(OrderRequestDTO orderRequestDTO) {
        if(orderRequestDTO == null) return null;
        Order order = new Order();
        order.setAddress(orderRequestDTO.getDireccion());
        order.setMail(orderRequestDTO.getEmail());
        order.setPhone(orderRequestDTO.getTelefono());
        order.setScheduleTime(LocalTime.parse(orderRequestDTO.getHorario()));
        order.setOrderDetails(orderRequestDTO.getDetalle().stream().map(OrderMapper::mapOrderDetail).collect(Collectors.toList()));
        return order;
    }

    private static OrderDetail mapOrderDetail(OrderDetailRequestDTO orderDetailRequestDTO) {
        if(orderDetailRequestDTO == null) return null;
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setQuantity(orderDetailRequestDTO.getCantidad());
        Product product = new Product();
        product.setProductID(orderDetailRequestDTO.getProducto());
        orderDetail.setProduct(product);
        return orderDetail;
    }

    public OrderResponseDTO mapOrderResponse(Order order) {
        if(order == null) return null;
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setFecha(order.getCreateDate());
        orderResponseDTO.setDireccion(order.getAddress());
        orderResponseDTO.setEmail(order.getMail());
        orderResponseDTO.setTelefono(order.getPhone());
        orderResponseDTO.setHorario(order.getScheduleTime());
        orderResponseDTO.setDetalle(
                order.getOrderDetails().stream()
                .map(orderDetail -> new OrderDetailResponseDTO(orderDetail.getProduct().getProductID(),
                        orderDetail.getProduct().getName(), orderDetail.getQuantity(), orderDetail.getDetailPrice()))
                .collect(Collectors.toList()));
        orderResponseDTO.setTotal(order.getAmount());
        orderResponseDTO.setDescuento(order.getDiscount());
        orderResponseDTO.setEstado(order.getState().toString());
        return orderResponseDTO;
    }

}
