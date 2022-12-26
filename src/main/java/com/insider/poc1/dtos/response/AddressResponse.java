package com.insider.poc1.dtos.response;


import lombok.Data;

@Data


public class AddressResponse {
    private String cep;

    private String bairro;

    private String localidade;

    private String uf;
    private Boolean isAddressPrincipal = false;

}
