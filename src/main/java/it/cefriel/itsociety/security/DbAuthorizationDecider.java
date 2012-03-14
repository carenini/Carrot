package it.cefriel.itsociety.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import it.cefriel.itsociety.security.commands.DataAccessRequest;
import org.cloudfoundry.org.codehaus.jackson.JsonParseException;
import org.cloudfoundry.org.codehaus.jackson.map.JsonMappingException;
import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.integration.annotation.Router;


public class DbAuthorizationDecider {
	private String rejected_channel=null;
	private String authorized_channel=null;
	private JdbcTemplate db_store=null;
	
	@Router
	public String check_authorization(Message m){
		boolean authorized=false;
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayInputStream in_body = new ByteArrayInputStream(m.getBody());
        try {
			DataAccessRequest req=mapper.readValue(in_body, DataAccessRequest.class);
			if (db_store!=null) authorized=check_db_auth(req);
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		if (authorized) return authorized_channel;
		else return rejected_channel;
		
	}
	
	private boolean check_db_auth(DataAccessRequest req) {
		return false;
	}

	public String getRejected_channel() {
		return rejected_channel;
	}

	public void setRejected_channel(String rejected_channel) {
		this.rejected_channel = rejected_channel;
	}

	public String getAuthorized_channel() {
		return authorized_channel;
	}

	public void setAuthorized_channel(String authorized_channel) {
		this.authorized_channel = authorized_channel;
	}

	public JdbcTemplate getDb_store() {
		return db_store;
	}

	public void setDb_store(JdbcTemplate db_store) {
		this.db_store = db_store;
	}

}