package it.cefriel.itsociety.security.authorization;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	@Id
	@Indexed
	private String uri=null;
	private Set<String> attributes=null;
	
	public User(String uri, Set<String> attributes) {
		super();
		this.uri = uri;
		this.attributes = attributes;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Set<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<String> attributes) {
		this.attributes = attributes;
	}
	
}
