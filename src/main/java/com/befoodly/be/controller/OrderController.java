package com.befoodly.be.controller;

import com.befoodly.be.entity.OrderEntity;
import com.befoodly.be.model.enums.OrderStatus;
import com.befoodly.be.model.request.OrderRequest;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addOrderToCart(@RequestBody OrderRequest orderRequest) {
        orderService.addOrderToCart(orderRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editOrderInCart(@RequestBody OrderRequest orderRequest,
                                             @RequestParam(value = "count", required = false) Integer orderCount,
                                             @RequestParam(value = "status", required = false) OrderStatus status) {
        orderService.editOrderInCart(orderRequest, orderCount, status);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/fetch/{customerReferenceId}")
    public ResponseEntity<GenericResponse<List<OrderEntity>>> fetchAllPendingOrders(@PathVariable(value = "customerReferenceId") String customerReferenceId) {
        List<OrderEntity> orderList = orderService.fetchAllPendingOrders(customerReferenceId);

        return new ResponseEntity<>(GenericResponse.<List<OrderEntity>>builder()
                .data(orderList)
                .statusCode(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

}
