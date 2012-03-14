package it.cefriel.itsociety.security.commands;

import org.springframework.jdbc.core.JdbcTemplate;

public class DataAccessRequest implements CommandRequest {
	private JdbcTemplate db=null;
	private String issuer=null;
	private String action=null;
	private String target=null;
	
	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void exec() {

	}

	public JdbcTemplate getDb() {
		return db;
	}

	public void setDb(JdbcTemplate db) {
		this.db = db;
	}

}
