package camel;

import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.ErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.LoggingLevel;
//import org.apache.camel.builder.ErrorHandlerBuilder;
//import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class TesteRoteamento {

	private static final int _SEGUNDOS = 30 * 1000;

	private static Logger log = Logger.getLogger(TesteRoteamento.class.getName());
	
	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new HttpToDB());
		context.start();
		new Scanner(System.in).nextLine();
		context.stop();

	}
	
	
	
	public void exemplo1() throws Exception{
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			public void configure() throws Exception {
				from("file:entrada?delay=5s")
				.log(LoggingLevel.INFO,"Processando o item ${id}")
				.to("file:saida");
			}
		});
		context.start();
		Thread.sleep(_SEGUNDOS);
		context.stop();
	}
	
	public void exemplo2()  throws Exception{
		//EXEMPLO 2
		CamelContext context = new DefaultCamelContext();
		
		RouteBuilder rotaValidacao = new RouteBuilder() {
			public void errorHandler(ErrorHandlerBuilder errorHandlerBuilder) {
				deadLetterChannel("file:falha")
				.useOriginalMessage();
			}
			
			public void configure() throws Exception {
				from("file:entrada?delay=5s")
				.to("validator:file:xsd/pedido.xsd")
				.log(LoggingLevel.INFO,"Processando o item ${id}")
				.to("file:saida");
			}
		};
		
		context.addRoutes(rotaValidacao);
		context.start();
		Thread.sleep(_SEGUNDOS);
		context.stop();
	}

	
	public void exemplo3()  throws Exception{
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new TrataErroXsd());
		context.start();
		Thread.sleep(_SEGUNDOS);
		context.stop();
	}

}
