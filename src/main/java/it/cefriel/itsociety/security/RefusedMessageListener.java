package it.cefriel.itsociety.security;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UnmatchedListener;

public class RefusedMessageListener implements UnmatchedListener {

	@Override
	public void update(EventBean arg0) {
        System.out.println("Message refused: "+ arg0.getUnderlying());

	}

}
