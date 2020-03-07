package br.com.dev.simples.erp.vendor.processor.rest;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.dev.simples.erp.vendor.util.CamelUtil;
import br.com.dev.simples.erp.vendor.util.DateUtil;

@Component
public class HeaderEstoqueProcessor implements Processor {

	@Value("${erp.empresas}")
	private String empresas;
	
	@Value("${erp.fabricantes}")
	private String fabricantes;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		
		final boolean manual 	 = CamelUtil.getBoolean(in, "manual");
		
		in.setHeader("dataEstoque",  getDataEstoque(manual, in));
		in.setHeader("listaEmpresas", empresas);
		in.setHeader("listaFabricantes", fabricantes);
	}

	private String getDataEstoque(boolean manual, Message in) {
		final String dataEstoque = in.getHeader("dataEstoque",String.class);
		return manual && StringUtils.isNotEmpty(dataEstoque) 
				? dataEstoque 
				: DateUtil.getDateerp();
	}
}
