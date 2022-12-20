package com.insider.poc1.service;

import com.insider.poc1.dtos.request.CustomerRequest;
import com.insider.poc1.dtos.response.CustomerResponse;
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
        if (existsByDocumentType(customerRequest.getDocumentType())) {
            throw new ExceptionConflict("Conflict: Document Type is already in use!");
        }
       CustomerModel save = customerRepository.save(mapper.map(customerRequest,CustomerModel.class));
        return mapper.map(save, CustomerResponse.class);

    }

    private boolean existsByDocumentType(DocumentType documentType) {
        return customerRepository.existsByDocumentType();
    }

    public CustomerRequest findAllId(UUID id){
      return customerRepository.findById(id)
              .map(customer -> mapper.map(customer, CustomerRequest.class))
              .orElseThrow(()-> new EmptyResultDataAccessException(1));
    }
    @Transactional
    public void deleteById(UUID id){
      Optional<CustomerModel> customerModelOptional = customerRepository.findById(id);
      customerModelOptional.orElseThrow(() -> new EmptyResultDataAccessException(1));
      customerRepository.deleteById(id);
    }

    public CustomerResponse update(UUID id, CustomerRequest customerRequest){
        var customerModel = customerRepository.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException(1));

      if(existsById(customerModel.getId())){
          CustomerModel save = customerRepository.save(mapper.map(customerRequest, CustomerModel.class));
          return mapper.map(save, CustomerResponse.class);
      }else{
          throw new EmptyResultDataAccessException(1);
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
     public Page<CustomerModel> findAll(Pageable pageable, DocumentType documentType) {
         return customerRepository.findAllCustomerByDocumentType(pageable, documentType);
     }


}


