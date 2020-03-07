package br.com.dev.simples.erp.vendor.model.erp.estoque;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Produto {

    private Integer codigoerp;
    private String codigoBarras;
    private String sku;
    private String descricao;
    private Boolean embalagemFracao;
    private Boolean inativo;
    private Boolean unidadeQuilo;
    private Double quantidade;
    
    public String getUnidadeQuiloCode() {
    	return unidadeQuilo == null ? "" : (unidadeQuilo ? "x" : "y");
    }
    
    public String getInativoString() {
    	return inativo != null ? (inativo ? "I" : "A") : "";
    }
    
    public String getCodigoBarrasvendor() {
    	return StringUtils.isEmpty(codigoBarras) ? sku.trim() : codigoBarras.trim();
    }
}
