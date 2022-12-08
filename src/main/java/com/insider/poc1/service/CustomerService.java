package com.insider.poc1.service;

import com.insider.poc1.dtos.request.CustomerRequest;
import com.insider.poc1.enums.DocumentType;
import com.insider.poc1.model.CustomerModel;
import com.insider.poc1.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final ModelMapper mapper;




    @Transactional//garante que se tudo volte ao normal, que não tenha dados quebrados;
    public CustomerModel save(CustomerRequest customerRequest) {

            if (existsByDocument(customerRequest.getDocument())) {
                throw new RuntimeException("Conflict: Document is already in use!");
            }
            if (existsByEmail(customerRequest.getEmail())) {
                throw new RuntimeException("Conflict: Email is already in use!");
            }
            if (existsByPhoneNumber(customerRequest.getPhoneNumber())) {
               throw new RuntimeException("Conflict: Phone Number is already in use!");
            }
            return customerRepository.save(mapper.map(customerRequest, CustomerModel.class));
    }

    public CustomerRequest findAllId(UUID id){
      return customerRepository.findById(id)
              .map(customer -> mapper.map(customer, CustomerRequest.class))
              .orElseThrow(()-> new RuntimeException("Customer not found."));
    }
    @Transactional
    public void deleteById(UUID id){
      Optional<CustomerModel> customerModelOptional = customerRepository.findById(id);
      customerModelOptional.orElseThrow(() -> new RuntimeException("Customer not foud."));
      customerRepository.deleteById(id);
    }

    public CustomerModel update(CustomerRequest customerRequest){
        var customerModel = new CustomerModel();
        BeanUtils.copyProperties(customerRequest, customerModel);
      if(existsById(customerModel.getId())){
          return customerRepository.save(mapper.map(customerRequest, CustomerModel.class));
      }else{
          throw new RuntimeException("Customer not foud.");
      }
    }
    public boolean existsByDocument(String document) {
        return customerRepository.existsByDocument(document);
    }


    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
    public boolean existsByPhoneNumber(String phoneNumber) {
        return customerRepository.existsByPhoneNumber(phoneNumber);
    }
    public boolean existsById(UUID id){
      return customerRepository.existsById(id);
    }
    public Page<CustomerModel> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
    public Optional<CustomerModel> findById(UUID id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public void delete(CustomerModel customerModel) {
        customerRepository.delete(customerModel);
    }
     public Page<CustomerModel> findAll(Pageable pageable, DocumentType documentType) {
         return customerRepository.findAllCustomerByDocumentType(pageable, documentType);
     }


}


