package com.example.machallenge.repositories;

import com.example.machallenge.models.entities.Order;

import java.time.LocalDate;
import java.util.Date;

public interface OrderRepository extends BaseRepository<Order>{

    Order findByCreateDate(LocalDate date);

}
