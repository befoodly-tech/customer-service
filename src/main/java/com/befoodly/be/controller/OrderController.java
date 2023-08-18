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

import java.util.Optional;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping("/add-to-cart/{customerReferenceId}")
    public ResponseEntity<?> addOrderToCart(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                            @RequestBody OrderRequest orderRequest) {
        orderService.addOrderToCart(customerReferenceId, orderRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/edit-cart/{customerReferenceId}")
    public ResponseEntity<GenericResponse<OrderResponse>> editOrderInCart(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                             @RequestBody OrderRequest orderRequest,
                                             @RequestParam(value = "count", required = false) Optional<Integer> orderCount) {
        OrderResponse orderResponse = orderService.editOrderInCart(customerReferenceId,orderRequest, orderCount);

        return new ResponseEntity<>(GenericResponse.<OrderResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(orderResponse)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update-cart/{customerReferenceId}")
    public ResponseEntity<GenericResponse<OrderResponse>> updateStatusOfCart(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                                                             @RequestParam(value = "status") OrderStatus status) {
        OrderResponse orderResponse = orderService.placeOrRemovePendingOrder(customerReferenceId, status);

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
