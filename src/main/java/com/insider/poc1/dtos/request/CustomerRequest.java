package com.insider.poc1.dtos.request;

import com.insider.poc1.config.documents.ClienteGroupSequenceProvider;
import com.insider.poc1.config.documents.CnpjGroup;
import com.insider.poc1.config.documents.CpfGroup;
import com.insider.poc1.enums.DocumentType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data

@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class CustomerRequest {

    @NotBlank(message = "Name ")
    @Length(message = "Name ")
    private String name;
    @NotBlank(message = "Email ")
    @Email(message = "Email ")
    private String email;
    @NotBlank(message = "Phone Number ")
    private String phoneNumber;
    @NotBlank(message = "Document ")
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups =  CnpjGroup.class)
    private String document;
    @NotNull(message = "Document Type ")
    private DocumentType documentType;

}








