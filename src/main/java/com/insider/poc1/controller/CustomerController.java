package com.insider.poc1.controller;

import com.insider.poc1.dtos.request.CustomerRequest;
import com.insider.poc1.dtos.response.CustomerResponse;
import com.insider.poc1.dtos.response.NewCustomerResponse;
import com.insider.poc1.enums.DocumentType;
import com.insider.poc1.model.CustomerModel;
import com.insider.poc1.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/customers")

public class CustomerController {

    private final CustomerService customerService;
    private final ModelMapper mapper;

    @PostMapping
    @CacheEvict(cacheNames = "customers", allEntries = true)
    @Operation(summary = "Created Customer.")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse saveCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return (mapper.map(customerService.save(customerRequest), CustomerResponse.class));
    }

    @GetMapping
    @Operation(summary = "Return all Customers.")
    public ResponseEntity<Page<CustomerResponse>> getAllCustomer(@PageableDefault(page = 0,
            size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(pageable)
                .map(customer -> (mapper.map(customer, CustomerResponse.class))));
    }
    @GetMapping("v2/{id}")
    @Operation(summary = "Return one Customer.")
    @ResponseStatus(HttpStatus.OK)
    public NewCustomerResponse getOneNewCustomerResponse(@PathVariable(value = "id") UUID id) {
        return mapper.map(customerService.findByIdNewResponse(id), NewCustomerResponse.class);
    }

    @Cacheable(value = "customers", key = "#id")
    @GetMapping("/{id}")
    @Operation(summary = "Return one Customer.")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getOneCustomer(@PathVariable(value = "id") UUID id) {
        return mapper.map(customerService.findCustomerById(id), CustomerResponse.class);
    }

    @GetMapping("/document-type/{documentType}")
    @Operation(summary = "Return Customers for document type.")
    public ResponseEntity<Page<CustomerResponse>> getAllCustomerByType(@PageableDefault(page = 0,
            size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, @PathVariable DocumentType documentType) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(pageable, documentType)
                .map(customer -> (mapper.map(customer, CustomerResponse.class))));
    }
    @GetMapping("/filter")
    @Operation(summary = "Filter all Customers.")
    public ResponseEntity<Page<CustomerResponse>> findCustomerByName(@PageableDefault(page = 0,
            size = 10, sort = {"name"}, direction = Sort.Direction.ASC) Pageable pageable, @RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findCustomerByName(pageable,name));


    }
    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "customers", key = "#Id")
    @Operation(summary = "Delete Customers.")
    public ResponseEntity<Object> deletecustomer(@PathVariable(value = "id") UUID id) {
        customerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
    }

    @PutMapping("/{id}")
    @CacheEvict(cacheNames = "customers", key = "#Id")
    @Operation(summary = "Update Customer.")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomerResponse updateCustomer(@PathVariable(value = "id") UUID id,
                                           @RequestBody @Valid CustomerModel customerModel) {
        customerModel.setId(id);
        customerService.update(id, new CustomerRequest());
        return mapper.map(customerService.findCustomerById(id), CustomerResponse.class);
    }


}
