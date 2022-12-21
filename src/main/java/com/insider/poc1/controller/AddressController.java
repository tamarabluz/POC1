package com.insider.poc1.controller;

import com.insider.poc1.dtos.request.AddressRequest;
import com.insider.poc1.dtos.response.AddressResponse;
import com.insider.poc1.model.AddressModel;
import com.insider.poc1.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;
    private final ModelMapper mapper;

    public AddressController(AddressService addressService, ModelMapper mapper) {
        this.addressService = addressService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse save(@RequestBody @Valid AddressRequest addressRequest)  {
        return (mapper.map(addressService.save(addressRequest), AddressResponse.class));
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Address deleted successfully");
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AddressResponse updateAddress(@PathVariable(value = "id") UUID id,
                                         @RequestBody @Valid AddressModel addressModel) {
        addressModel.setId(id);
        addressService.update(id, new AddressRequest());
        return mapper.map(addressService.findAllId(id), AddressResponse.class);
    }
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public AddressResponse AddressUpdate(@RequestBody @Valid UUID id){
        addressService.AddressUpdate(id);
        return mapper.map(addressService.findById(id), AddressResponse.class);

    }
}
