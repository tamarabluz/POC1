package com.insider.poc1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insider.poc1.dtos.response.CustomerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressModel {



    @Id
    @GeneratedValue(generator = "hibernate-uuid")  //para mysql
    @Type(type = "org.hibernate.type.UUIDCharType")  //para mysql
    @Column
    private UUID id;

    @Column
    private String cep;
    @Column
    private String logradouro;
    @Column
    private String bairro;
    @Column
    private String complemento;
    @Column
    private String localidade;
    @Column
    private String uf;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerModel customerModel;



}
