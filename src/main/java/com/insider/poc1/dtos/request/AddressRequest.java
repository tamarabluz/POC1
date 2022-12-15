package com.insider.poc1.dtos.request;


import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


@Data
public class AddressRequest {
    @NotBlank
    //@Pattern(regexp = "\\d{5}-\\d{3}")
    private String cep;

    private String logradouro;

    private String bairro;

    private int numero;

    private String localidade;
   
    private String uf;

    private Boolean isAddressPrincipal = false;
    private UUID customerModelId;


}
