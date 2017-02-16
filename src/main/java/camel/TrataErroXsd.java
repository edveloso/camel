package camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.ErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;

public class TrataErroXsd extends RouteBuilder {

	public void errorHandler(ErrorHandlerBuilder errorHandlerBuilder) {
		deadLetterChannel("file:falha")
		.useOriginalMessage();
	}

	public void configure() throws Exception {
		from("file:entrada?delay=5s")
		.to("validator:file:xsd/pedido.xsd")
		.log(LoggingLevel.INFO, "Processando o item ${id}")
		.to("file:saida");
	}

}
