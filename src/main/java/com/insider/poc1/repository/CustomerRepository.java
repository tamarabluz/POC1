package com.insider.poc1.repository;

import com.insider.poc1.enums.DocumentType;
import com.insider.poc1.model.CustomerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, UUID> {
    boolean existsByDocument(String document);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Page<CustomerModel> findAllCustomerByDocumentType(Pageable pageable, DocumentType documentType);
}
