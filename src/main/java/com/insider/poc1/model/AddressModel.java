package com.insider.poc1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

//    @JsonIgnore
//    @OneToMany
//    @JoinColumn(mappedBy ="customer", fetch = FetchType.LAZY)
//    private List<AddressModel> addressList = new ArrayList<>();

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
