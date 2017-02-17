package camel;

import org.apache.camel.builder.RouteBuilder;

public class HttpToMock extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("http://localhost:8088/fj36-livraria/loja/livros/mais-vendidos")
		  .delay(1000)
		  .unmarshal()
		  .json() 
		  .process(new PoolingServicoLivrosProcessorBean())
		  .log("${body}")
		  .to("mock:livros");
		
	}

}
