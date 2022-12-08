package com.insider.poc1.controller;

import com.insider.poc1.dtos.request.CustomerRequest;
import com.insider.poc1.dtos.response.CustomerResponse;
import com.insider.poc1.enums.DocumentType;
import com.insider.poc1.model.CustomerModel;
import com.insider.poc1.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;;
import java.util.*;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/customers")
public class CustomerControler {

    private final CustomerService customerService;
    private final ModelMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse saveCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return (mapper.map(customerService.save(customerRequest), CustomerResponse.class));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomer(@PageableDefault(page = 0,
            size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(pageable)
                .map(customer -> (mapper.map(customer, CustomerResponse.class))));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getOneCustomer(@PathVariable(value = "id") UUID id) {
        return mapper.map(customerService.findById(id), CustomerResponse.class);
    }

    @GetMapping("/document-type/{documentType}")
    public ResponseEntity<Page<CustomerModel>> getAllCustomerByType(@PageableDefault(page = 0,
            size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @PathVariable DocumentType documentType) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(pageable, documentType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletecustomer(@PathVariable(value = "id") UUID id) {
        customerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse updateCustomer(@PathVariable(value = "id") UUID id,
                                           @RequestBody @Valid CustomerModel customerModel) {
        customerModel.setId(id);
        customerService.update(new CustomerRequest());
        return mapper.map(customerService.findAllId(id), CustomerResponse.class);
    }

}
