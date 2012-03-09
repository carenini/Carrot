package it.cefriel.itsociety.security;

import org.springframework.amqp.core.Message;
import org.springframework.jdbc.core.JdbcTemplate;

public class Executor {
	private JdbcTemplate db=null;

	public JdbcTemplate getDb() {
		return db;
	}

	public void setDb(JdbcTemplate db) {
		this.db = db;
	}

	public void exec (Message m){
		
	}
	
}
