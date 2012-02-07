package it.cefriel.itsociety.security;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.core.Queue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class ACLEnforcer {

	private static final long serialVersionUID = 3666344516911658738L;
	private AmqpAdmin amqpAdmin=null;
    private AmqpTemplate amqpTemplate=null;
    private Queue inQ=null;
    private String inQ_name="input_queue";
    private SimpleMessageListenerContainer container=null;
    private ConnectionFactory connectionFactory=null;

    public ACLEnforcer(String userid) {
		ApplicationContext context = new ClassPathXmlApplicationContext("rabbitConfiguration.xml");
		amqpAdmin = context.getBean(AmqpAdmin.class);
		amqpTemplate = context.getBean(AmqpTemplate.class);
		connectionFactory = context.getBean(ConnectionFactory.class);
        System.out.println("ACL servlet started");

        inQ=new Queue(inQ_name);
        amqpAdmin.declareQueue(inQ);
        System.out.println("Amqp admin: "+amqpAdmin);
        System.out.println("Amqp template: "+amqpTemplate);
        System.out.println("Amqp conn: "+connectionFactory);

        /* --- Setup CEP --- */
        Configuration cepConfig = new Configuration();
        //TODO: define the event object
        cepConfig.addEventType("IncomingMessage",Tick.class.getName());
        EPServiceProvider cep = EPServiceProviderManager.getProvider("ITSociety_ACL",cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
        EPAdministrator cepAdm = cep.getEPAdministrator();
        //TODO: define the query
        EPStatement cepStatement = cepAdm.createEPL("select * from " +
                "StockTick(symbol='AAPL').win:length(2) " +
                "having avg(price) > 6.0");
        cepStatement.addListener(new CepGoodMessageListener());
        
        /* --- Setup message listener --- */
        container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(inQ_name);
        //container.setMessageListener(new MessageListenerAdapter(new HelloWorldHandler()));
    }

}
