package br.com.dev.simples.erp.vendor.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dev.simples.erp.vendor.processor.arquivos.ClientesProcessor;
import br.com.dev.simples.erp.vendor.processor.arquivos.VendasProcessor;

@Component
public class NotasRoute extends RouteBuilder{
	
	private final String ROUTE_ID =  "Notas";
	
	@Autowired
	private ClientesProcessor clienteProcessor;

	@Autowired
	private VendasProcessor vendasProcessor;

	@Override
	public void configure() throws Exception {
		
		from("direct:notas").id(ROUTE_ID)
			.log(LoggingLevel.TRACE, "Rota " + ROUTE_ID + " iniciada")
			.streamCaching()
			
			// Carrega as notas do dia
			.setHeader("trimestral").constant(Boolean.FALSE)
			.to("direct:carregaNotas")
			
			//Salva as notas em um property
			.setProperty("notas").body()
			
			.process(clienteProcessor)
			.to("direct:mtrix")

			.process(vendasProcessor)
			.to("direct:mtrix")
			
			.log(LoggingLevel.TRACE, "Rota " + ROUTE_ID + " concluída");
	
	}

}
