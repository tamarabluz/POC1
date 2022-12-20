package com.insider.poc1.dtos.request;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;


@Data
public class AddressRequest {
    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{3}")
    @Length(min = 9, max = 9)
    private String cep;


    private String logradouro;


    private String bairro;

    @NotNull
    private int numero;


    private String localidade;


    private String uf;

    @NotNull
    private Boolean isAddressPrincipal = false;

    @NotNull
    private UUID customerModelId;


}
