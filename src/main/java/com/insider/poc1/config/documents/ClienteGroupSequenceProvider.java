package com.insider.poc1.config.documents;

import com.insider.poc1.dtos.request.CustomerRequest;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<CustomerRequest> {

    //Implementando método obrigatório
    //Na primeira linha é instanciado uma lista;
    //Na segunda é add a classe para não perder outras anotaçoes;
    //É feita uma verificação para evitar exception;
    //Por fim, é pego o grupo do cliente que é selecionado;
    @Override
    public List<Class<?>> getValidationGroups(CustomerRequest customerRequest) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(CustomerRequest.class);
        if (isDocumentType(customerRequest)) {
            groups.add(customerRequest.getDocumentType().getGroup());
        }
        return groups;
    }

    protected boolean isDocumentType(CustomerRequest customerRequest) {
        return customerRequest != null && customerRequest.getDocumentType() != null;
    }
}

