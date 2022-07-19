package com.example.machallenge.services;

import com.example.machallenge.models.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService extends BaseService<Order> {

    List<Order> findAllByCreateDate(LocalDate date);

}
