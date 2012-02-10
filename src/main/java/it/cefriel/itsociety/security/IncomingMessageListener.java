package it.cefriel.itsociety.security;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;


public class IncomingMessageListener implements MessageListener {
	private EPRuntime cepRT=null;
	
    public IncomingMessageListener(){
    	super();
    	EPServiceProvider cep=null;
        Configuration cepConfig = null; 
        EPAdministrator cepAdm = null; 
        EPStatement cepStatement = null; 
        
        System.out.println(" ==== CEP Message listener started =====");
        /* --- Setup CEP --- */
        cepConfig= new Configuration();
        
        cepConfig.addEventType("IncomingMessage",Message.class.getName());
        cep = EPServiceProviderManager.getProvider("ITSociety_ACL",cepConfig);
        cepRT= cep.getEPRuntime();
        cepAdm=cep.getEPAdministrator();
        //TODO: define the query
        cepStatement=cepAdm.createEPL("select * from IncomingMessage");
        cepStatement.addListener(new CepGoodMessageListener());
        System.out.println("CEPRT= "+cepRT);
    }

    public void onMessage(Message message) {
        System.out.println("CEPRT= "+cepRT);
        cepRT.sendEvent(message);
    }
}
