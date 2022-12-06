package com.insider.poc1.response;

import com.insider.poc1.model.AddressModel;
import javax.validation.constraints.NotEmpty;

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

