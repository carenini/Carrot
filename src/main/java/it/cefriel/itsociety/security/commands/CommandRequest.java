package it.cefriel.itsociety.security.commands;

public interface CommandRequest {

    public String getIssuer();

    public String getAction();

    public void run();
}
