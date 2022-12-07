package com.insider.poc1.enums;

import com.insider.poc1.config.documents.CnpjGroup;
import com.insider.poc1.config.documents.CpfGroup;

public enum DocumentType {
    PJ("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class),
    PF("Física", "CPF", "000.000.000-00", CpfGroup.class);

    private final String descricao;
    private final String documento;
    private final String mascara;
    private final Class<?> group;

    DocumentType(String descricao, String documento, String mascara, Class<?> group) {
        this.descricao = descricao;
        this.documento = documento;
        this.mascara = mascara;
        this.group = group;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDocumento() {
        return documento;
    }

    public String getMascara() {
        return mascara;
    }

    public Class<?> getGroup() {
        return group;
    }
}
