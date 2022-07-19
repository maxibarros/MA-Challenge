package com.example.machallenge.services;

import com.example.machallenge.models.Order;
import com.example.machallenge.models.OrderDetail;
import com.example.machallenge.models.OrderState;
import com.example.machallenge.models.Product;
import com.example.machallenge.repositories.BaseRepository;
import com.example.machallenge.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public OrderServiceImpl(BaseRepository<Order> baseRepository) {
        super(baseRepository);
    }

    @Override
    @Transactional
    public Order save(Order order){
        //TODO: evito recursividad creando una lista, pero igualmente el debugger se clava
        order.setCreateDate(LocalDate.now());
        /*List<OrderDetail> orderDetailsList = new ArrayList<>();
        for(OrderDetail od: order.getOrderDetails()) {
            Optional<Product> p = productService.findByProductID(od.getProduct().getProductID());
            if(p.isPresent()) {
                OrderDetail detail = new OrderDetail();
                detail.setProduct(p.get());
                detail.setQuantity(od.getQuantity());
                detail.setDetailPrice(detail.calculateDetailPrice());
                detail.setOrder(order);
                orderDetailsList.add(detail);
            } else throw new RuntimeException("Producto no encontrado.");
        }
        order.setOrderDetails(orderDetailsList);

         */
        for(OrderDetail od: order.getOrderDetails()) {
            Optional<Product> p = productService.findByProductID(od.getProduct().getProductID());
            if(p.isPresent()) {
                od.setOrder(order);
                od.setProduct(p.get());
                od.setDetailPrice(od.calculateDetailPrice());
            } else throw new RuntimeException("Producto no encontrado.");
        }

        if(order.applyToDiscount()) {
            order.setDiscount(true);
            order.setAmount(order.calculateTotalAmountWithDiscount());
        } else {
            order.setDiscount(false);
            order.setAmount(order.calculateTotalAmount());
        }
        order.setState(OrderState.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAllByCreateDate(LocalDate date) {
        return orderRepository.findAllByCreateDate(date);
    }
}
