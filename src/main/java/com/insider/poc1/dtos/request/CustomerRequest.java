package com.insider.poc1.dtos.request;

import com.insider.poc1.config.documents.ClienteGroupSequenceProvider;
import com.insider.poc1.config.documents.CnpjGroup;
import com.insider.poc1.config.documents.CpfGroup;
import com.insider.poc1.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class CustomerRequest {

    @NotBlank
    UUID id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    private String document;
    private DocumentType documentType;

}



