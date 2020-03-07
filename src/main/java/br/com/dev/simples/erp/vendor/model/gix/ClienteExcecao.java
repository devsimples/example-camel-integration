package br.com.dev.simples.erp.vendor.model.erp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ClienteExcecao {

	@Value("${cliente.codigo}")
	private Integer codigo;
	
	@Value("${cliente.cpf}")
	private String cpf;
	
	@Value("${cliente.endereco}")
	private String endereco;
	
	@Value("${cliente.cep}")
	private String cep;
	
	@Value("${cliente.bairro}")
	private String bairro;
	
	@Value("${cliente.cidade}")
	private String cidade;
	
	@Value("${cliente.estado}")
	private String estado;
}
