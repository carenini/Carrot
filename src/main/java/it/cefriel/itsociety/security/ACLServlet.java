package it.cefriel.itsociety.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ACLServlet extends HttpServlet {

	private static final long serialVersionUID = 3666344516911658738L;

    public void init() throws ServletException {
    	ACLEnforcer e=new ACLEnforcer("");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println("ACL servlet started");
    }
}
