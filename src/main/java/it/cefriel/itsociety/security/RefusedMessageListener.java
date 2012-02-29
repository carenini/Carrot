package it.cefriel.itsociety.security;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UnmatchedListener;

public class RefusedMessageListener implements UnmatchedListener {
	private AmqpTemplate amqp=null;
	private String target_exchange=null;

	public AmqpTemplate getAmqp() {
		return amqp;
	}

	public void setAmqp(AmqpTemplate amqp) {
		this.amqp = amqp;
	}

	public String getTarget_exchange() {
		return target_exchange;
	}

	public void setTarget_exchange(String target_exchange) {
		this.target_exchange = target_exchange;
	}

	@Override
	public void update(EventBean arg0) {
		String routing_key=null;
		Message bad_message=null;
		
        System.out.println("Message refused: "+ arg0.getUnderlying());
        bad_message=(Message) arg0.getUnderlying();
        routing_key=bad_message.getMessageProperties().getReceivedRoutingKey()+".refused";
        amqp.send(target_exchange,routing_key,bad_message);
	}

}
