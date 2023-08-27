package com.befoodly.be.controller;

import com.befoodly.be.model.request.CookCreateRequest;
import com.befoodly.be.model.response.CookProfileResponse;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.service.CookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/cook")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class CookController {

    @Autowired
    private final CookService cookService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createCookProfile (@RequestBody CookCreateRequest request) {
        cookService.createCookForVendor(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/fetch/popular")
    public ResponseEntity<GenericResponse<List<CookProfileResponse>>> fetchAllPopularCookProfiles (@RequestParam(value = "orderCounts") Long orderCounts) {
        List<CookProfileResponse> cookProfileList = cookService.fetchPopularCooks(orderCounts);

        return new ResponseEntity<>(GenericResponse.<List<CookProfileResponse>>builder()
                .data(cookProfileList)
                .statusCode(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "/fetch/{vendorId}")
    public ResponseEntity<GenericResponse<List<CookProfileResponse>>> fetchAllVendorCookProfiles (@PathVariable(value = "vendorId") Long vendorId) {
        List<CookProfileResponse> cookProfileList = cookService.fetchAllCooksForVendor(vendorId);

        return new ResponseEntity<>(GenericResponse.<List<CookProfileResponse>>builder()
                .data(cookProfileList)
                .statusCode(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GenericResponse<CookProfileResponse>> updateCookOrderCounts (@PathVariable(value = "id") Long id,
                                                                                       @RequestParam(value = "orderCounts") Long orderCounts) {
        CookProfileResponse response = cookService.updateCookOrderCounts(id, orderCounts);

        return new ResponseEntity<>(GenericResponse.<CookProfileResponse>builder()
                .data(response)
                .statusCode(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}
