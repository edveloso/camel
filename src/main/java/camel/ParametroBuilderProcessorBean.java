package camel;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import br.com.caelum.livraria.modelo.Livro;

public class ParametroBuilderProcessorBean implements Processor {

	public void process(Exchange exchange) throws Exception {

	  Message inBound = exchange.getIn();
	  
	  Livro livro = (Livro) inBound.getBody();

	  String nomeAutor = livro.getNomeAutor();

	  inBound.setHeader("nomeAutor", nomeAutor);
	  
	}

}
