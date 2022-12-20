package com.insider.poc1.repository;

import com.insider.poc1.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, UUID>{

    boolean existsByLogradouroNumero(String logradouro, int numero);
}
