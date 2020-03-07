package br.com.dev.simples.erp.vendor.routes.mtrix;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MtrixFileRoute extends RouteBuilder {

	private final String ROUTE_ID = "MTRIX";

	@Value("${mtrix.folder}")
	private String folder;

	@Override
	public void configure() throws Exception {

		from("direct:mtrix").id(ROUTE_ID)
			.log(LoggingLevel.TRACE, "Rota " + ROUTE_ID + " iniciada")
			.split(body())
				.setHeader("nomeArquivo", simple("${body.nomeArquivo}"))
				
				.convertBodyTo(String.class)
				.to("file:" + folder + "?fileName=${header.nomeArquivo}")
				
				.log(LoggingLevel.TRACE, "Novo arquivo gerado: ${header.nomeArquivo}")

			.end();

	}

}
