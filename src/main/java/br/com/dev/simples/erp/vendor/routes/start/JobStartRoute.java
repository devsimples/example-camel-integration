package br.com.dev.simples.erp.vendor.routes.start;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JobStartRoute extends RouteBuilder {

	private static final String NOTAS_START = "Start Notas";
	private static final String NOTAS_START_TRIMESTRAL = "Start Notas Trimestral";
	private static final String NOTAS_ESTOQUE = "Rota de partida Estoque";
	
	@Value("${job.cron}")
	private String cron;
	

	@Override
	public void configure() throws Exception {
		
		from("quartz2:CronNotas?cron=" + cron).id(NOTAS_START)
			.log(LoggingLevel.TRACE, "Rota " + NOTAS_START + " iniciada")
			.to("direct:notas");
		
		from("quartz2:CronNotasTrimestral?cron=" + cron).id(NOTAS_START_TRIMESTRAL)
			.log(LoggingLevel.TRACE, "Rota " + NOTAS_START_TRIMESTRAL + " iniciada")
			.to("direct:notasTrimestral");
		
		from("quartz2:CronEstoque?cron=" + cron).id(NOTAS_ESTOQUE)
			.log(LoggingLevel.TRACE, "Rota " + NOTAS_ESTOQUE + " iniciada")
			.to("direct:estoque");
		
	}

}
