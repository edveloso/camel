package camel;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class PoolingServicoLivrosProcessorBean implements Processor {

	public void process(Exchange exchange) throws Exception {

		List<?> lista = (List<?>)exchange.getIn().getBody();
	  
		Message message = exchange.getIn();
	  System.out.println(lista.get(1));
		
		message.setBody(lista.get(0));
	  
	}

}
