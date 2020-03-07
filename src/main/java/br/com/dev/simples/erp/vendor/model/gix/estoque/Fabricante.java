package br.com.dev.simples.erp.vendor.model.erp.estoque;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fabricante {

    private Integer codigo;
    private String cnpj;
    private String nome;
}
