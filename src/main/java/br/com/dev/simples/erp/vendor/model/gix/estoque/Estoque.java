package br.com.dev.simples.erp.vendor.model.erp.estoque;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.dev.simples.erp.vendor.model.erp.Empresa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Estoque{

    private Empresa empresa = new Empresa();
    private Fabricante fabricante = new Fabricante();
    private Produto produto = new Produto();
}
