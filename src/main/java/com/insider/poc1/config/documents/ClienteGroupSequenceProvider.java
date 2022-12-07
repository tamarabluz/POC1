package com.insider.poc1.config.documents;

import com.insider.poc1.model.CustomerModel;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<CustomerModel> {

    //Implementando método obrigatório
    //Na primeira linha é instanciado uma lista;
    //Na segunda é add a classe para não perder outras anotaçoes;
    //É feita uma verificação para evitar exception;
    //Por fim, é pego o grupo do cliente que é selecionado;
    @Override
    public List<Class<?>> getValidationGroups(CustomerModel customerModel) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(CustomerModel.class);
        if (isDocumentType(customerModel)) {
            groups.add(customerModel.getDocumentType().getGroup());
        }
        return groups;
    }

    protected boolean isDocumentType(CustomerModel customerModel) {
        return customerModel != null && customerModel.getDocumentType() != null;
    }
}

