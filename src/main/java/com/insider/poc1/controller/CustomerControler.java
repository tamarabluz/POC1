package com.insider.poc1.controller;

import com.insider.poc1.dtos.request.CustomerRequest;
import com.insider.poc1.dtos.response.CustomerResponse;
import com.insider.poc1.model.CustomerModel;
import com.insider.poc1.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/poc1")
public class CustomerControler {

    final CustomerService customerService;

    public CustomerControler(CustomerService customerService) {
        this.customerService = customerService;}


    @PostMapping
    public ResponseEntity<Object> saveCustomer(@RequestBody @Valid CustomerRequest customerRequest){
        //Verificação para caso já exista um cadastro com o mesmo documento.
        if(customerService.existsByDocument(customerRequest.getDocument())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Document is already in use!");
        }
        var customerModel = new CustomerModel();
        BeanUtils.copyProperties(customerRequest, customerModel);//converte dto em model;
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerModel));
    }
    @GetMapping
    public ResponseEntity<List<CustomerModel>> getAllCustomer(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCustomer(@PathVariable(value = "id") UUID id){
        Optional<CustomerModel> customerModelOptional= customerService.findById(id);
        if(!customerModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerModelOptional.get());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletecustomer(@PathVariable(value = "id") UUID id){
        Optional<CustomerModel> customerModelOptional = customerService.findById(id);
        if(!customerModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }
        customerService.delete(customerModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable(value = "id") UUID id,
    @RequestBody @Valid CustomerRequest customerRequest){
        Optional<CustomerModel> customerModelOptional = customerService.findById(id);
        if(customerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }
        var customerModel = new CustomerModel();
        BeanUtils.copyProperties(customerRequest, customerModel);
        customerModel.setId(customerModelOptional.get().getId());

        return ResponseEntity.status(HttpStatus.OK).body(customerService.save(customerModel));
    }

}
