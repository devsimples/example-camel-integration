package br.com.dev.simples.erp.vendor.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.dev.simples.erp.vendor.processor.arquivos.EstoqueProcessor;
import br.com.dev.simples.erp.vendor.processor.arquivos.ProdutosProcessor;

@Component
public class EstoqueRoute extends RouteBuilder{

	private final String ROUTE_ID =  "Estoque";
	
	@Value("${erp.estoque.rest.url}")
	private String urlEstoque;

	@Autowired
	private EstoqueProcessor estoqueProcessor;

	@Autowired
	private ProdutosProcessor produtosProcessor;
	
	@Override
	public void configure() throws Exception {

		from("direct:estoque").id(ROUTE_ID)
			.log(LoggingLevel.TRACE, "Rota " + ROUTE_ID + " iniciada")
			.streamCaching()

			.to("direct:carregaEstoque")

			//Salva as notas em um property
			.setProperty("estoque").body()

			//Gera o arquivo de estoque
			.process(estoqueProcessor)
			.to("direct:mtrix")

			//Gera o arquivo de produtos
			.process(produtosProcessor)
			.to("direct:mtrix")

			.log(LoggingLevel.TRACE, "Rota " + ROUTE_ID + " concluída");
		
	}

}
