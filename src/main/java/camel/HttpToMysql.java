package camel;

import org.apache.camel.builder.RouteBuilder;

public class HttpToMysql extends RouteBuilder {

	
	@Override
	public void configure() throws Exception {
		
		from("http://localhost:8088/fj36-livraria/loja/livros/mais-vendidos")
			  .delay(1000)
			  .unmarshal()
			  .json() 
			  .process(new PoolingServicoLivrosProcessorBean())
			  .log("${body}")
	    .to("direct:livros");
		
		from("direct:livros")
			.split(body())
			.process(new ParametroBuilderProcessorBean()) 
			.setBody(simple("insert into Livros(nomeAutor) values ('${header[nomeAutor]}')"))
		.to("jdbc:mysqlDataSource?useHeadersAsParameters=true");
		
		
	}

}
