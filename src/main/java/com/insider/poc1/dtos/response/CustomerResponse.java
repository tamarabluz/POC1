package com.insider.poc1.dtos.response;

import com.insider.poc1.model.AddressModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

        @NotEmpty
        private String name;
        @NotEmpty
        private String email;
        @NotEmpty
        private String phoneNumber;
        @NotEmpty
        private AddressModel addressModel;
}

