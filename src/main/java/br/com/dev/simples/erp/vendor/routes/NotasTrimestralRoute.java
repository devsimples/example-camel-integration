package br.com.dev.simples.erp.vendor.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dev.simples.erp.vendor.processor.arquivos.ForcaDeVendasProcessor;

@Component
public class NotasTrimestralRoute extends RouteBuilder{

	private final String ROUTE_ID =  "Notas Trimestral";
	
	@Autowired
	private ForcaDeVendasProcessor forcaDeVendasProcessor;
	
	@Override
	public void configure() throws Exception {
		
		from("direct:notasTrimestral").id(ROUTE_ID)
			.log(LoggingLevel.TRACE, "Rota " + ROUTE_ID + " iniciada")
			.streamCaching()
			
			// Carrega as notas do último trimestre
			.setHeader("trimestral").constant(Boolean.TRUE)
			.to("direct:carregaNotas")
			
			.setProperty("notasTrimestral").body()
			
			//Salva o arquivo
			.process(forcaDeVendasProcessor)
			.to("direct:mtrix")

			.log(LoggingLevel.TRACE, "Rota " + ROUTE_ID + " concluída");
	
	}

}
