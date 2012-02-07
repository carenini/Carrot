package it.cefriel.itsociety.security;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class CepGoodMessageListener implements UpdateListener {

    public CepGoodMessageListener() {
        
    }

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {
		// TODO Auto-generated method stub
        System.out.println("Event received: "+ newData[0].getUnderlying());
	}
}


