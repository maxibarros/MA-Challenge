package com.example.machallenge.mappers;

import com.example.machallenge.models.dto.OrderDTO;
import com.example.machallenge.models.dto.OrderDetailDTO;
import com.example.machallenge.models.dto.ProductDTO;
import com.example.machallenge.models.entities.Order;
import com.example.machallenge.models.entities.OrderDetail;
import com.example.machallenge.models.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO mapOrder(Order order);

    Order mapOrder(OrderDTO orderDTO);

    OrderDetailDTO mapOrderDetail(OrderDetail orderDetail);

    OrderDetail mapOrderDetail(OrderDetailDTO orderDetailDTO);

    ProductDTO mapProduct(Product product);

    Product mapProduct(ProductDTO productDTO);
}
