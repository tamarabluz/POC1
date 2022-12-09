package com.insider.poc1.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AddressResponse {
    private String cep;

    private String bairro;

    private String localidade;

    private String uf;

}
