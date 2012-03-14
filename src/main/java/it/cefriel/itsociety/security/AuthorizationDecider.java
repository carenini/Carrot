package it.cefriel.itsociety.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.cefriel.itsociety.security.authorization.Resource;
import it.cefriel.itsociety.security.authorization.User;
import it.cefriel.itsociety.security.commands.DataAccessRequest;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.cloudfoundry.org.codehaus.jackson.JsonParseException;
import org.cloudfoundry.org.codehaus.jackson.map.JsonMappingException;
import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.integration.annotation.Router;


public class AuthorizationDecider {
	private String rejected_channel=null;
	private String authorized_channel=null;
	private JdbcTemplate db_store=null;
	private MongoTemplate nosql_store=null;
	
	@Router
	public String check_authorization(Message m){
		boolean authorized=false;
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayInputStream in_body = new ByteArrayInputStream(m.getBody());
        try {
			DataAccessRequest req=mapper.readValue(in_body, DataAccessRequest.class);
			if (db_store!=null) authorized=check_db_auth(req);
			else if(nosql_store!=null) authorized=check_nosql_auth(req);
			
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

	private boolean check_nosql_auth(DataAccessRequest req) {
		List<Resource> resources=null;
		List<User> users=null;
		String requested_action=null;
		Set<String> required_attr_set=null;
		Set<String> offered_attr_set=null;
		
		resources=nosql_store.find(new Query(where("uri").is(req.getTarget())), Resource.class);
		users=nosql_store.find(new Query(where("uri").is(req.getIssuer())), User.class);

		requested_action=req.getAction();
		
		//FIXME there should be only one resource found per request, enforce it!
		for (User u : users) {
			offered_attr_set=u.getAttributes();
			if (offered_attr_set != null) {
				for (Resource r : resources) {
					required_attr_set = r.getWhitelist(requested_action);
					if (required_attr_set != null) {
						Set<String> intersection = new HashSet<String>(required_attr_set);
						intersection.retainAll(offered_attr_set);
						if (intersection.size() != 0)
							return true;
					}
				}
			}
		}
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

	public MongoTemplate getNosql_store() {
		return nosql_store;
	}

	public void setNosql_store(MongoTemplate nosql_store) {
		this.nosql_store = nosql_store;
	}

}
