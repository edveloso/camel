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
import org.apache.camel.util.jndi.JndiContext;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;


public class TesteRoteamento {

	private static final int _SEGUNDOS = 30 * 1000;

	private static Logger log = Logger.getLogger(TesteRoteamento.class.getName());
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
		ds.setDatabaseName("fj36_camel");
		ds.setServerName("localhost");
		ds.setPort(3306);
		ds.setUser("root");
		ds.setPassword("");
		
		JndiContext jndi = new JndiContext();
		jndi.rebind("mysqlDataSource", ds);
		
		CamelContext context = new DefaultCamelContext(jndi);
		context.addRoutes(new HttpToMysql());
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
	
	public void exemplo4()throws Exception{
		
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new HttpToMock());
		context.start();
		new Scanner(System.in).nextLine();
		context.stop();
		
	}

}
