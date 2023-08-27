package com.befoodly.be.controller;

import com.befoodly.be.model.request.ProductCreateRequest;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.model.response.ProductDataResponse;
import com.befoodly.be.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateRequest request) {
        productService.createNewProduct(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/fetch/all")
    public ResponseEntity<GenericResponse<List<ProductDataResponse>>> fetchAllActiveProduct() {
        List<ProductDataResponse> activeProducts = productService.fetchAllActiveProducts();

        return new ResponseEntity<>(GenericResponse.<List<ProductDataResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(activeProducts)
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "/fetch/{vendorId}")
    public ResponseEntity<GenericResponse<List<ProductDataResponse>>> fetchActiveProductsByVendor
            (@PathVariable(value = "vendorId") Long vendorId) {
        List<ProductDataResponse> activeProducts = productService.fetchAllActiveProducts();

        return new ResponseEntity<>(GenericResponse.<List<ProductDataResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(activeProducts)
                .build(), HttpStatus.OK);
    }
}
