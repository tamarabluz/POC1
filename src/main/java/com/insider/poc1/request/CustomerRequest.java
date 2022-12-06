package com.insider.poc1.dto.request;

import com.insider.poc1.enums.DocumentType;
import com.insider.poc1.model.AddressModel;
import com.insider.poc1.model.CustomerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CustomerRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty(message = "Documento válido deve ser informado.")
    private String document;
    @NotEmpty
    private DocumentType documentType;
    @NotEmpty
    private AddressModel addressModel;
}



