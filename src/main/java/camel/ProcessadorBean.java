package camel;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class ProcessadorBean implements Processor {

	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
	  List<?> lista = (List<?>)exchange.getIn().getBody();
	  lista.get(0);
	  
	  Message message = exchange.getIn();
	  message.setBody(lista);
	  
	}

}
