package camel;

import java.util.logging.Logger;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class TesteRoteamento {

	private static final int _SEGUNDOS = 30 * 1000;

	private static Logger log = Logger.getLogger(TesteRoteamento.class.getName());
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

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

}
