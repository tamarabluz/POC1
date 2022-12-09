package com.insider.poc1.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class AddressResponse {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AddressRequest {

        private String district;

        private String city;

        private String zipCode;

        private String state;

    }
}
