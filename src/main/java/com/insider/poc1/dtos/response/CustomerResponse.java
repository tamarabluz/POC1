package com.insider.poc1.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

        private String name;

        private String email;
        private List<AddressResponse> addressList = new ArrayList<>();

}

