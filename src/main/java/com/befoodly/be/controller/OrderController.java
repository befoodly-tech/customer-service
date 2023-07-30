package com.befoodly.be.controller;

import com.befoodly.be.model.enums.OrderStatus;
import com.befoodly.be.model.request.OrderRequest;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.model.response.OrderResponse;
import com.befoodly.be.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<GenericResponse<OrderResponse>> removeOrderInCart(@RequestBody OrderRequest orderRequest,
                                             @RequestParam(value = "count", required = false) Optional<Integer> orderCount,
                                             @RequestParam(value = "status", required = false) OrderStatus status) {
        OrderResponse orderResponse = orderService.removeOrderInCart(orderRequest, orderCount, status);

        return new ResponseEntity<>(GenericResponse.<OrderResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(orderResponse)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/fetch/{customerReferenceId}")
    public ResponseEntity<GenericResponse<OrderResponse>> fetchAllPendingOrders(@PathVariable(value = "customerReferenceId") String customerReferenceId) {
        OrderResponse orderList = orderService.fetchAllPendingOrders(customerReferenceId);

        return new ResponseEntity<>(GenericResponse.<OrderResponse>builder()
                .data(orderList)
                .statusCode(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

}
