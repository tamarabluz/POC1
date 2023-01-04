package com.insider.poc1.model;

import com.insider.poc1.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Data
@Builder
@OptimisticLocking(type = OptimisticLockType.VERSION)
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
    private String document;
    @Column
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customerModel")
    private List<AddressModel> addressList = new ArrayList<>();

    @Version
    private Long version;
}
