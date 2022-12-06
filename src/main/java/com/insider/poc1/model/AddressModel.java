package com.insider.poc1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String street;
    @Column
    private String number;
    @Column
    private String district;
    @Column
    private String city;
    @Column
    private String zipCode;
    @Column
    private String state;

}
