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

        private String name;

        private String email;


}

