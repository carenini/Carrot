package it.cefriel.itsociety.security;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MessageHandler  implements MessageListener  {

    public MessageHandler(){
    	super();
    	System.out.println("Message listener started, I LIVE!");
    }

	public void onMessage(Message arg0) {
        System.out.println("Received: " + arg0);		
	}
}
