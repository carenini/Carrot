package it.cefriel.itsociety.security.commands;

import org.springframework.jdbc.core.JdbcTemplate;

public class DataAccessRequest implements CommandRequest {
	private JdbcTemplate db=null;

	
	public String getIssuer() {
		return null;
	}

	public String getAction() {
		return null;
	}

	public String getTarget() {
		return null;
	}

	public void run() {

	}

	public JdbcTemplate getDb() {
		return db;
	}

	public void setDb(JdbcTemplate db) {
		this.db = db;
	}

}
