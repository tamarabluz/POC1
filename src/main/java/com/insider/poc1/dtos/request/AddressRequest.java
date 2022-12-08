package com.insider.poc1.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @NotBlank
    private String street;
    @NotBlank
    private String number;
    @NotBlank
    private String district;
    @NotBlank
    private String city;
    @Pattern(regexp = "\\d{5}-\\d{3}")
    @NotBlank
    private String zipCode;
    @NotBlank
    private String state;

}
