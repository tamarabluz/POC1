package com.insider.poc1.dtos.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class CustomerResponse {

        private String name;

        private String email;
        private List<AddressResponse> addressList = new ArrayList<>();

        public CustomerResponse() {
        }
}

