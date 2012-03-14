package it.cefriel.itsociety.security.authorization;

import java.util.Hashtable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Resource {
	
	@Id
	@Indexed
	private String uri=null;
	private Hashtable<String, Set<String>> access_table=null;
		
	public Resource(String uri,	Hashtable<String, Set<String>> access_table) {
		super();
		this.uri = uri;
		this.access_table = access_table;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	public Hashtable<String, Set<String>> getAccess_table() {
		return access_table;
	}
	public void setAccess_table(Hashtable<String, Set<String>> access_table) {
		this.access_table = access_table;
	}
	
	public Set<String> getWhitelist(String operation){
		if ((access_table!=null)&&(access_table.containsKey(operation))) return access_table.get(operation);
		else return null;
	}
	
}
