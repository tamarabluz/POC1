package com.insider.poc1.service;

import com.insider.poc1.enums.DocumentType;
import com.insider.poc1.model.CustomerModel;
import com.insider.poc1.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
  final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Transactional//garante que se tudo volte ao normal, que não tenha dados quebrados;
    public CustomerModel save(CustomerModel customerModel) {
        return customerRepository.save(customerModel);
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
