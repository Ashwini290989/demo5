package org.example.demo5;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends Application {

}

/**
 * The @ApplicationPath  centralizes the base URL for all REST endpoints in your application, making it easier to organize and manage routes.
 * If you ever need to change the base path (e.g., from /api to /services), you only need to update it in one place.
 *Hello Application sets uo the REST API , by extending application ,lets server know its in charge of setting API*/