package it.cefriel.itsociety.security;

import org.springframework.amqp.core.Message;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class CepGoodMessageListener implements UpdateListener {
	
    public CepGoodMessageListener() {
        
    }

	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
        System.out.println("Event received: "+ newData[0].getUnderlying());
        Message good_message=(Message) newData[0].getUnderlying();
	}
}


