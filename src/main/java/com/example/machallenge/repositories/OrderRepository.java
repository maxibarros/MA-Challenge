package com.example.machallenge.repositories;

import com.example.machallenge.models.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends BaseRepository<Order>{

    List<Order> findAllByCreateDate(LocalDate date);

}
