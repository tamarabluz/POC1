package com.insider.poc1.service;

import com.insider.poc1.dtos.request.CustomerRequest;
import com.insider.poc1.dtos.response.CustomerResponse;
import com.insider.poc1.dtos.response.NewCustomerResponse;
import com.insider.poc1.enums.DocumentType;
import com.insider.poc1.exception.ExceptionConflict;
import com.insider.poc1.model.CustomerModel;
import com.insider.poc1.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final ModelMapper mapper;



    @Transactional//garante que se tudo volte ao normal, que não tenha dados quebrados;
    public CustomerResponse save(CustomerRequest customerRequest) {

            if (existsByDocument(customerRequest.getDocument())) {
                throw new ExceptionConflict("Conflict: Document is already in use!");
            }
            if (existsByEmail(customerRequest.getEmail())) {
                throw new ExceptionConflict("Conflict: Email is already in use!");
            }
            if (existsByPhoneNumber(customerRequest.getPhoneNumber())) {
               throw new ExceptionConflict("Conflict: Phone Number is already in use!");
            }

       CustomerModel save = customerRepository.save(mapper.map(customerRequest,CustomerModel.class));
        return mapper.map(save, CustomerResponse.class);

    }


    public CustomerRequest findCustomerById(UUID id){
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return customerRepository.findById(id)
                .map(customer -> mapper.map(customer, CustomerRequest.class))
                .orElseThrow(() -> new EmptyResultDataAccessException("Id not found.", 1));
    }
    @Transactional
    public void deleteById(UUID id){
      Optional<CustomerModel> customerModelOptional = customerRepository.findById(id);
      customerModelOptional.orElseThrow(() -> new EmptyResultDataAccessException("Customer not found.", 1));
      customerRepository.deleteById(id);
    }

    public CustomerResponse update(UUID id, CustomerRequest customerRequest) {
        try {
            var customerModel = customerRepository.findById(id).orElseThrow(
                    () -> new EmptyResultDataAccessException("Id not found.", 1));
            mapper.map(customerRequest, customerModel);

            CustomerModel updatedCustomerModel = customerRepository.save(customerModel);
            return mapper.map(updatedCustomerModel, CustomerResponse.class);
        } catch (EmptyResultDataAccessException ex) {
            throw ex;
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

    public Page<CustomerResponse> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(c -> mapper.map(c, CustomerResponse.class));
    }
     public Page<CustomerResponse> findAll(Pageable pageable, DocumentType documentType) {
         return customerRepository.findAllCustomerByDocumentType(pageable, documentType)
                 .map(c -> mapper.map(c, CustomerResponse.class));
     }

    public Page<CustomerResponse> findCustomerByName(Pageable pageable, String name) {
        return customerRepository.findByNameContains(pageable, name)
                .map(customer -> mapper.map(customer, CustomerResponse.class));
    }

    public Optional<NewCustomerResponse> findByIdNewResponse(UUID id) {
        return customerRepository.findById(id).map(customerModel -> mapper.map(customerModel, NewCustomerResponse.class));
    }
}



