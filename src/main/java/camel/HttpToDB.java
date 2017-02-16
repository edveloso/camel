package camel;

import org.apache.camel.builder.RouteBuilder;

public class HttpToDB extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("http://localhost:8088/fj36-livraria/loja/livros/mais-vendidos")
		  .delay(1000)
		  .unmarshal()
		  .json() 
		  .process(new ProcessadorBean())
		  .log("${body}")
		  .to("mock:livros");
		
	}

}
