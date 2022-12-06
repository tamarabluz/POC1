package com.insider.poc1.dtos.request;

import com.insider.poc1.enums.DocumentType;
import com.insider.poc1.model.AddressModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "O campo nome não pode estar em branco")
    private String name;
    @NotBlank(message = "O campo email não pode estar em branco")
    private String email;
    @NotBlank(message = "O campo número não pode estar em branco")
    private String phoneNumber;
    @NotBlank(message = "Documento válido deve ser informado.")
    private String document;
    private DocumentType documentType;
    //private AddressModel addressModel;
}



