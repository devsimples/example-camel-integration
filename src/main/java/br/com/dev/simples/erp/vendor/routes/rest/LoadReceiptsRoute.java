package br.com.dev.simples.erp.vendor.routes.rest;


@Component
public class LoadReceiptsRoute extends RouteBuilder {

	private final String ROUTE_ID = "Carrega Notas erp";

	@Value("${erp.notas.rest.url}")
	private String urlNotas;
	
	private final String queryParameter = "?dataInicial=${header.dataInicial}"
			+ "&dataFinal=${header.dataFinal}"
			+ "&listaEmpresas=${header.listaEmpresas}"
			+ "&listaFabricantes=${header.listaFabricantes}"
			+ "&listaOperacoes=${header.listaOperacoes}"
			+ "&registros=5000";

	@Autowired
	private HeaderNotasProcessor headerProcessor;
	
	@Autowired
	private HeaderAuthenticationProcessor authProcessor;

	@Override
	public void configure() throws Exception {
		//.setBody().simple(FileUtil.readAll("src/main/resources/requests/notas.json"))
		from("direct:carregaNotas").id(ROUTE_ID)
			.streamCaching()
			.log(LoggingLevel.INFO, "Carregando Notas...")
			
			// Inicializa headers
			.setHeader(Exchange.HTTP_METHOD).constant(HttpMethod.GET)
			.process(headerProcessor)
			.process(authProcessor)

			//Recupera as Notas
			.log(LoggingLevel.INFO, "Carregando URL=" + urlNotas + " QUERY_PARAMETERS=" + queryParameter)
			.setHeader(Exchange.HTTP_QUERY, simple(queryParameter))
			.to(urlNotas)

			.log(LoggingLevel.INFO, "Notas carregadas")

			// Converte o json para objeto Java
			.unmarshal(RouteUtil.getDefaultFormat(ContentNota.class));
	}

}
