package com.insider.poc1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
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
    private int numero;
    @Column
    private String localidade;
    @Column
    private String uf;
    @Column
    private Boolean isAddressPrincipal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerModel customerModel;

    @Version
    private Long version;



}
