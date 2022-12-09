package com.insider.poc1.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @NotBlank
    //@Pattern(regexp = "\\d{5}-\\d{3}")
    private String cep;

    //@JsonIgnore

    private String logradouro;

    private String bairro;
    @NotBlank
    private String complemento;

    private String localidade;
   
    private String uf;
    private CustomerRequest customerRequest;


}
