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
public class HeaderNotasProcessor implements Processor {

	@Value("${erp.empresas}")
	private String empresas;
	
	@Value("${erp.fabricantes}")
	private String fabricantes;
	
	@Value("${erp.operacoes}")
	private String operacoes;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
		
		in.setHeader("dataInicial", getDataInicial(in));
		in.setHeader("dataFinal", getDataFinal(in));
		in.setHeader("listaEmpresas", empresas);
		in.setHeader("listaFabricantes", fabricantes);
		in.setHeader("listaOperacoes", operacoes);
	}
	
	private String getDataInicial(Message in) {
		final Boolean trimestral 	= CamelUtil.getBoolean(in, "trimestral");
		final Boolean manual 		= CamelUtil.getBoolean(in, "manual");
		
		final String dataInicial 		= trimestral ? DateUtil.getDateerpLess90days() : DateUtil.getDateerp();
		final String dataInicialManual 	= in.getHeader("dataInicial",String.class);
		
		return (manual && StringUtils.isNoneEmpty(dataInicialManual)) ? dataInicialManual : dataInicial;
	}
	
	private String getDataFinal(Message in) {
		final Boolean manual 		  = CamelUtil.getBoolean(in, "manual");
		final String dataFinalManual  = in.getHeader("dataFinal",String.class);
		
		return (manual && StringUtils.isNoneEmpty(dataFinalManual)) ? dataFinalManual : DateUtil.getDateerp();
	}
	

}
