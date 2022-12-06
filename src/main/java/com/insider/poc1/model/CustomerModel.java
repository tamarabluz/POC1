package com.insider.poc1.model;

import com.insider.poc1.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "hibernate-uuid")  //para mysql
    @Type(type="org.hibernate.type.UUIDCharType")  //para mysql
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    private String email;
    @Column
    private String phoneNumber;
    @Column
    @CPF
    private String document;
    @Column
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
//    @OneToMany
//    @JoinColumn(name = "idAddress", referencedColumnName = "id")
//    private AddressModel addressModel;



}
