package com.insider.poc1.dtos.request;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;


@Data
public class AddressRequest {
    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{3}")
    @Length(min = 8, max = 8)
    private String cep;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String bairro;

    @NotNull
    private int numero;

    @NotBlank
    private String localidade;

    @NotBlank
    private String uf;

    @NotBlank
    private Boolean isAddressPrincipal = false;

    @NotNull
    private UUID customerModelId;


}
