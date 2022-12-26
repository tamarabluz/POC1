package com.insider.poc1.dtos.response;

import com.insider.poc1.enums.DocumentType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewCustomerResponse {
        private String name;

        private String email;

        private String phoneNumber;

        private DocumentType documentType;
        private List<AddressResponse> addressList = new ArrayList<>();

    }
