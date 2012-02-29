package it.cefriel.itsociety.security;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationMethodRef;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;


public class IncomingMessageListener implements MessageListener {
	private EPRuntime cepRT=null;
    private int cache_size=10;
	
    public IncomingMessageListener(){
    	super();
    	EPServiceProvider cep=null;
        Configuration cepConfig = null; 
        EPAdministrator cepAdm = null; 
        EPStatement cepStatement = null;
        ConfigurationMethodRef cache_config=null;
               
        System.out.println(" ==== CEP Message listener started =====");
        /* --- Setup CEP --- */
        cepConfig= new Configuration();
        cache_config=new ConfigurationMethodRef();
        cache_config.setLRUCache(cache_size);
        cepConfig.addEventType("IncomingMessage",Message.class.getName());
        cepConfig.addMethodRef("it.cefriel.itsociety.security.AclEntry",cache_config);
        cep = EPServiceProviderManager.getProvider("ITSociety_ACL",cepConfig);
        cepRT= cep.getEPRuntime();
        cepAdm=cep.getEPAdministrator();
        //TODO: define the query
        cepStatement=cepAdm.createEPL("select * from IncomingMessage");
        cepStatement.addListener(new CepGoodMessageListener());
        cepRT.setUnmatchedListener(new RefusedMessageListener());
        System.out.println("CEPRT= "+cepRT);
    }

    public int getCache_size() {
		return cache_size;
	}

	public void setCache_size(int cache_size) {
		this.cache_size = cache_size;
	}

	public void onMessage(Message message) {
        System.out.println("CEPRT= "+cepRT);
        cepRT.sendEvent(message);
    }
}
