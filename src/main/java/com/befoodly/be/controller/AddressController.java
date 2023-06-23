package com.befoodly.be.controller;

import com.befoodly.be.entity.AddressEntity;
import com.befoodly.be.model.request.AddressCreateRequest;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AddressController {

    @Autowired
    private final AddressService addressService;

    @PostMapping("/address/{customerReferenceId}")
    public ResponseEntity<GenericResponse<String>> addAddress(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                                              @RequestBody AddressCreateRequest request) {
        String referenceId = addressService.addAddress(customerReferenceId, request);

        return new ResponseEntity<>(GenericResponse.<String>builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(referenceId)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/address/edit/{customerReferenceId}")
    public ResponseEntity<GenericResponse<AddressEntity>> editAddress(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                                                      @RequestParam(value = "title") String title,
                                                                      @RequestBody AddressCreateRequest addressCreateRequest) {
        AddressEntity address = addressService.editAddress(customerReferenceId, title, addressCreateRequest);

        return new ResponseEntity<>(GenericResponse.<AddressEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .data(address)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/address/delete/{customerReferenceId}")
    public ResponseEntity<?> deleteAddress(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                          @RequestParam(value = "title") String title){
        addressService.deleteAddress(customerReferenceId, title);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
