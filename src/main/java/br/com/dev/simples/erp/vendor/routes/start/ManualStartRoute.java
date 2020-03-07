package br.com.dev.simples.erp.vendor.routes.start;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.dev.simples.erp.vendor.processor.arquivos.ClientesProcessor;
import br.com.dev.simples.erp.vendor.processor.arquivos.EstoqueProcessor;
import br.com.dev.simples.erp.vendor.processor.arquivos.ForcaDeVendasProcessor;
import br.com.dev.simples.erp.vendor.processor.arquivos.ProdutosProcessor;
import br.com.dev.simples.erp.vendor.processor.arquivos.VendasProcessor;

@Component
public class ManualStartRoute extends RouteBuilder{

	private static final String MANUAL_START_CLIENTES = "Manual Start Clientes";
	private static final String MANUAL_START_VENDAS = "Manual Start Vendas";
	private static final String MANUAL_START_FORCA_DE_VENDAS = "Manual Start Força de Vendas";

	private static final String MANUAL_START_ESTOQUE = "Manual Start Estoque";
	private static final String MANUAL_START_PRODUTOS = "Manual Start Produtos";
	
	@Autowired
	private ClientesProcessor clienteProcessor;

	@Autowired
	private VendasProcessor vendasProcessor;
	
	@Autowired
	private ForcaDeVendasProcessor forcaDeVendasProcessor;
	
	@Autowired
	private EstoqueProcessor estoqueProcessor;

	@Autowired
	private ProdutosProcessor produtosProcessor;
	
	@Override
	public void configure() throws Exception {
		
		setupNotasRoutes();
		setupEstoqueRoutes();
	}
	
	private void setupNotasRoutes() {
		from("direct:manualStartClientes").id(MANUAL_START_CLIENTES)
			.log(LoggingLevel.INFO, "Rota " + MANUAL_START_CLIENTES + " iniciada")
			.streamCaching()
			.setHeader("manual").constant(Boolean.TRUE)
			
			.setHeader("trimestral").constant(Boolean.FALSE)
			.to("direct:carregaNotas")
			.setProperty("notas").body()
			.process(clienteProcessor).to("direct:mtrix")
			
			.log(LoggingLevel.INFO, "Rota " + MANUAL_START_CLIENTES + " concluída");
	
		from("direct:manualStartVendas").id(MANUAL_START_VENDAS)
			.log(LoggingLevel.INFO, "Rota " + MANUAL_START_VENDAS + " iniciada")
			.streamCaching()
			.setHeader("manual").constant(Boolean.TRUE)
			
			.setHeader("trimestral").constant(Boolean.FALSE)
			.to("direct:carregaNotas")
			.setProperty("notas").body()
			.process(vendasProcessor).to("direct:mtrix")
			
			.log(LoggingLevel.INFO, "Rota " + MANUAL_START_VENDAS + " concluída");
		
		from("direct:manualStartForcaDeVendas").id(MANUAL_START_FORCA_DE_VENDAS)
			.log(LoggingLevel.INFO, "Rota " + MANUAL_START_FORCA_DE_VENDAS + " iniciada")
			.streamCaching()
			.setHeader("manual").constant(Boolean.TRUE)
			
			.setHeader("trimestral").constant(Boolean.TRUE)
			.to("direct:carregaNotas")
			.setProperty("notasTrimestral").body()
			.process(forcaDeVendasProcessor)
			.to("direct:mtrix")
			
			.log(LoggingLevel.INFO, "Rota " + MANUAL_START_FORCA_DE_VENDAS + " concluída");
	}
	
	
	private void setupEstoqueRoutes() {
		from("direct:manualStartEstoque").id(MANUAL_START_ESTOQUE)
			.log(LoggingLevel.INFO, "Rota " + MANUAL_START_ESTOQUE + " iniciada")
			.streamCaching()
			.setHeader("manual").constant(Boolean.TRUE)
			
			.to("direct:carregaEstoque")
			.setProperty("estoque").body()
			.process(estoqueProcessor)
			.to("direct:mtrix")
			
			.log(LoggingLevel.INFO, "Rota " + MANUAL_START_ESTOQUE + " concluída");
	
		from("direct:manualStartProdutos").id(MANUAL_START_PRODUTOS)
			.log(LoggingLevel.INFO, "Rota " + MANUAL_START_PRODUTOS + " iniciada")
			.streamCaching()
			.setHeader("manual").constant(Boolean.TRUE)
			
			.to("direct:carregaEstoque")
			.setProperty("estoque").body()
			.process(produtosProcessor)
			.to("direct:mtrix")
			
			.log(LoggingLevel.INFO, "Rota " + MANUAL_START_PRODUTOS + " concluída");
	}

}
	