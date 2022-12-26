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

    @NotBlank(message = "Name ",groups = {CustomerRequest.class, CpfGroup.class, CnpjGroup.class})
    @Length(message = "Name ", min = 10, max = 50, groups = {CustomerRequest.class, CpfGroup.class, CnpjGroup.class})
    private String name;
    @NotBlank(message = "Email ",  groups = {CustomerRequest.class, CpfGroup.class, CnpjGroup.class})
    @Email(message = "Email ", groups = {CustomerRequest.class, CpfGroup.class, CnpjGroup.class})
    private String email;
    @NotBlank(message = "Phone Number ", groups = {CustomerRequest.class, CpfGroup.class, CnpjGroup.class})
    private String phoneNumber;
    @NotBlank(message = "Document ", groups = {CpfGroup.class, CnpjGroup.class})
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups =  CnpjGroup.class)
    private String document;
    @NotNull(message = "Document Type ", groups = {CustomerRequest.class, CpfGroup.class, CnpjGroup.class})
    private DocumentType documentType;

}








