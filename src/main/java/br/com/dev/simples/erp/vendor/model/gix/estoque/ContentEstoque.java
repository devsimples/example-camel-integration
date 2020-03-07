package br.com.dev.simples.erp.vendor.model.erp.estoque;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentEstoque{

	private List<Estoque> content = new ArrayList<Estoque>();
}
