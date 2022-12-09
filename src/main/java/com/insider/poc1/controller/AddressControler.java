package com.insider.poc1.controller;

import com.insider.poc1.dtos.request.AddressRequest;
import com.insider.poc1.dtos.response.AddressResponse;
import com.insider.poc1.model.AddressModel;
import com.insider.poc1.service.AddressService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/address")
public class AddressControler {

    private final AddressService addressService;
    private final ModelMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse saveAddress(@RequestBody @Valid AddressRequest addressRequest) throws IOException {
        return (mapper.map(addressService.searchZipCode(addressRequest), AddressResponse.class));
    }
    @GetMapping
    public ResponseEntity<Page<AddressResponse>> getAllAddress(@PageableDefault(page = 0,
            size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll(pageable)
                .map(address -> (mapper.map(address, AddressResponse.class))));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponse getOneAddress(@PathVariable(value = "id") UUID id) {
        return mapper.map(addressService.findById(id), AddressResponse.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAddress(@PathVariable(value = "id") UUID id) {
        addressService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Address deleted successfully");
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponse updateAddress(@PathVariable(value = "id") UUID id,
                                         @RequestBody @Valid AddressModel addressModel) {
        addressModel.setId(id);
        addressService.update(new AddressRequest());
        return mapper.map(addressService.findAllId(id), AddressResponse.class);
    }


}
