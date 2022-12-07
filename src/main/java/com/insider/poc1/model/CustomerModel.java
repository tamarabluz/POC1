package com.insider.poc1.model;

import com.insider.poc1.config.documents.ClienteGroupSequenceProvider;
import com.insider.poc1.config.documents.CnpjGroup;
import com.insider.poc1.config.documents.CpfGroup;
import com.insider.poc1.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
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
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    private String document;
    @Column
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
//    @OneToMany
//    @JoinColumn(mappedBy ="customer", fetch = FetchType.LAZY)
//    private List<AddressModel> addressList = new ArrayList<>();



}
