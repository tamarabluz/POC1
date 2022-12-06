package com.insider.poc1.controller;

import com.insider.poc1.dto.request.CustomerRequest;
import com.insider.poc1.model.CustomerModel;
import com.insider.poc1.response.CustomerResponse;
import com.insider.poc1.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/poc1")
public class CustomerControler {

    final CustomerService customerService;

    public CustomerControler(CustomerService customerService) {
        this.customerService = customerService;}

}
