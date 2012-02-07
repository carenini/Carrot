package it.cefriel.itsociety.security;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import com.espertech.esper.client.EPRuntime;


public class IncomingMessageListener implements MessageListener {
	private EPRuntime cepRT=null;
	
    public IncomingMessageListener(EPRuntime engine){
    	cepRT=engine;
    }

    public void onMessage(Message message) {
        cepRT.sendEvent(message);
    }
}
