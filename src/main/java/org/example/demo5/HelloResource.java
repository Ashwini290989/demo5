package org.example.demo5;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}

/**
 * When a client (like a  Postman) sends a GET request to http://<server>/hello-world:
 * The server finds this HelloResource class because of the @Path("/hello-world").
 * It looks for a method that handles GET requests.
 * The hello() method is called.
 * The server sends the string "Hello, World!" back to the client as plain text.
 */