package com.insider.poc1.dtos.request;

import com.insider.poc1.config.documents.ClienteGroupSequenceProvider;
import com.insider.poc1.config.documents.CnpjGroup;
import com.insider.poc1.config.documents.CpfGroup;
import com.insider.poc1.enums.DocumentType;
import com.insider.poc1.model.AddressModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class CustomerRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    private String document;
    private DocumentType documentType;
    //private AddressModel addressModel;
}



