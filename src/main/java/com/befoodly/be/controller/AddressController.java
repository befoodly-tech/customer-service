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

import java.util.List;

@RestController
@RequestMapping("/v1/address")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class AddressController {

    @Autowired
    private final AddressService addressService;

    @PostMapping("/{customerReferenceId}")
    public ResponseEntity<GenericResponse<AddressEntity>> addAddress(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                                              @RequestBody AddressCreateRequest request) {
        AddressEntity addressEntity = addressService.addAddress(customerReferenceId, request);

        return new ResponseEntity<>(GenericResponse.<AddressEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(addressEntity)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{customerReferenceId}")
    public ResponseEntity<GenericResponse<AddressEntity>> editAddress(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                                                      @RequestParam(value = "title") String title,
                                                                      @RequestBody AddressCreateRequest addressCreateRequest) {
        AddressEntity address = addressService.editAddress(customerReferenceId, title, addressCreateRequest);

        return new ResponseEntity<>(GenericResponse.<AddressEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .data(address)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerReferenceId}")
    public ResponseEntity<?> deleteAddress(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                          @RequestParam(value = "title") String title){
        addressService.deleteAddress(customerReferenceId, title);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{customerReferenceId}")
    public ResponseEntity<GenericResponse<List<AddressEntity>>> fetchAllAddressesForCustomer
            (@PathVariable(value = "customerReferenceId") String customerReferenceId){
        List<AddressEntity> addressEntityList = addressService.fetchAddressList(customerReferenceId);

        return new ResponseEntity<>(GenericResponse.<List<AddressEntity>>builder()
                .data(addressEntityList)
                .statusCode(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}
