package com.example.machallenge.controllers;

import com.example.machallenge.mappers.pedidos.OrderMapper;
import com.example.machallenge.mappers.pedidos.dto.OrderRequestDTO;
import com.example.machallenge.mappers.pedidos.dto.OrderResponseDTO;
import com.example.machallenge.models.Order;
import com.example.machallenge.repositories.BaseRepository;
import com.example.machallenge.services.OrderService;
import com.example.machallenge.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class OrderController extends BaseController<Order> {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    public OrderController(BaseRepository<Order> baseRepository) {
        super(baseRepository);
    }

    @PostMapping
    public ResponseEntity<?> saveOrder(@Validated @RequestBody OrderRequestDTO orderRequestDTO, BindingResult result) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(super.validate(result));
            }
            Order order = orderMapper.mapOrderRequest(orderRequestDTO);
            order.getOrderDetails().stream()
                    .forEach(orderDetail -> {
                        if(!productService.existsByProductID(orderDetail.getProduct().getProductID())){
                            responseMap.put("error", "Producto " + orderDetail.getProduct().getProductID() + " no existe.");
                        }
                    });
            if(!responseMap.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            order = orderService.save(order);
            OrderResponseDTO orderResponseDTO = orderMapper.mapOrderResponse(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
        }  catch(DateTimeParseException e) {
            responseMap.put("error", "Formato de horario incorrecto (HH:mm).");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<?> findOrdersByDate(@RequestParam(name = "fecha") String fecha){
        try {
            LocalDate createDate = LocalDate.parse(fecha);
            List<Order> allOrdersByCreateDate = orderService.findAllByCreateDate(createDate);
            List<OrderResponseDTO> orderResponseDTOList = allOrdersByCreateDate.stream()
                    .map(orderMapper::mapOrderResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(orderResponseDTOList);
        } catch(DateTimeParseException e) {
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "Formato de fecha incorrecto (yyyy-MM-dd).");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
