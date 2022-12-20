package com.insider.poc1.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String messageUser;
    private String messageDev;

}
