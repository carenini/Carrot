package it.cefriel.itsociety.security;

import org.springframework.amqp.core.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.integration.annotation.Router;


public class AuthorizationDecider {
	private String rejected_channel=null;
	private String authorized_channel=null;
	private JdbcTemplate db=null;
	
	@Router
	public String check_authorization(Message m){
		boolean authorized=false;
		
		if (authorized) return authorized_channel;
		else return rejected_channel;
		
	}
	
	public JdbcTemplate getDb() {
		return db;
	}

	public void setDb(JdbcTemplate db) {
		this.db = db;
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

}
