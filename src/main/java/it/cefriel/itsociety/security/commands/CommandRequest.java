package it.cefriel.itsociety.security.commands;

public interface CommandRequest {

    public String getIssuer();

    public String getAction();
    
    public String getTarget();

    public void run();
}
