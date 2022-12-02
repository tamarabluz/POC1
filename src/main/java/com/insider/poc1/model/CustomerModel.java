package com.insider.poc1.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

public class CustomerModel {
    @Id
    @GeneratedValue(generator = "hibernate-uuid")  //para mysql
    @Type(type="org.hibernate.type.UUIDCharType")  //para mysql
    @Column
    private UUID id;

    @Column
    private String telefone;
    @Column
    @CPF
    private String cpf;
    @Column
    @CNPJ
    private String cnpj;
    @Column
    private String endereco;
    @Column
    private String tipo;





}
