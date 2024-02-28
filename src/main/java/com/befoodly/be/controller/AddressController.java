package com.befoodly.be.controller;

import com.befoodly.be.entity.Address;
import com.befoodly.be.model.request.AddressCreateRequest;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/address")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{customerReferenceId}")
    public ResponseEntity<GenericResponse<Address>> addAddress(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                                               @RequestBody AddressCreateRequest request) {
        Address address = addressService.addAddress(customerReferenceId, request);

        return new ResponseEntity<>(GenericResponse.<Address>builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(address)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{customerReferenceId}")
    public ResponseEntity<GenericResponse<Address>> editAddress(@PathVariable(value = "customerReferenceId") String customerReferenceId,
                                                                @RequestParam(value = "title") String title,
                                                                @RequestBody AddressCreateRequest addressCreateRequest) {
        Address address = addressService.editAddress(customerReferenceId, title, addressCreateRequest);

        return new ResponseEntity<>(GenericResponse.<Address>builder()
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
    public ResponseEntity<GenericResponse<List<Address>>> fetchAllAddressesForCustomer
            (@PathVariable(value = "customerReferenceId") String customerReferenceId){
        List<Address> addressList = addressService.fetchAddressList(customerReferenceId);

        return new ResponseEntity<>(GenericResponse.<List<Address>>builder()
                .data(addressList)
                .statusCode(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}
