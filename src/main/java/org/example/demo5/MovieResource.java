package org.example.demo5;

import jakarta.inject.Inject; // Allows DI to automatically provide required objects
import jakarta.ws.rs.*;       //Bring in tools to build a REST _API
import jakarta.ws.rs.core.MediaType; // This define the type of data i.e JSON,TEXT PLAIN
import jakarta.ws.rs.core.Response;  // create responses for API
import org.example.demo5.db.MovieRepository; // API call MovieRepository to do actual work
import org.example.demo5.model.Movie; //API call Movie to do actual work
import java.util.List; //store collection of movies


@Path("/movies")
//base URI path, it makes the class accessible via movies, when client makes HTTP request.
public class MovieResource {
    // class starts
    @Inject
    // used for DI, automatically creates an object
    private MovieRepository movieRepository;
    //variable holds instance of movie repository provided via Inject.

    @GET // maps this method to the HTTP GET request, when client sent GET request to movies. method execute
    @Produces(MediaType.APPLICATION_JSON) // format of the response
    public Response getAllMovies() {  // method to get all movies
        List<Movie> movies = movieRepository.getAllMovies(); //used to hold multiple movies. calling the method from MR
        return Response.ok(movies).build(); //creating response, finalizing the response , and prepare it to send to the client.


    } // get all movies method ends

    @GET
    @Path("/{id}") // this method will handle request to Movies/id
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("id") Long id) { //Binds the dynamic {id} part of the URL to the id parameter in the method.
        Movie movieid = movieRepository.getMovieById(id);
        if (movieid != null) { // if movie found creates response with ok.
            return Response.ok(movieid).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }


    @POST // creating new movies
    @Consumes(MediaType.APPLICATION_JSON)// method accepts JSON format
    @Produces({MediaType.TEXT_PLAIN}) // response will be in text format
    public Response CreateMovie(Movie movie) {
        movieRepository.CreateMovie(movie);// call the create movie method and saves the movie to the database
        return Response.status(Response.Status.CREATED) // 201,new response created
                .entity("CreatedMovie") //response in plain text format
                .build(); // finalize the response

    }

    @PUT // update movie
    @Path("/{id}") // path,ID of the movie which is to be updated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("id") Long id,Movie updatedMovie) {
        Movie existingmovie = movieRepository.getMovieById(id);// updates movie data
        if (existingmovie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
            existingmovie.setTitle(updatedMovie.getTitle());
            existingmovie.setGenre(updatedMovie.getGenre());
            existingmovie.setReleaseyear(updatedMovie.getReleaseyear());
            existingmovie.setDirector(updatedMovie.getDirector());
            existingmovie.setSummery(updatedMovie.getSummery());
            movieRepository.updateMovie( existingmovie);
            return Response.ok("Movie updated successfully").build();


    }

    @DELETE // remove a movie
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id") Long id) {
        Movie deleteMovie = movieRepository.getMovieById(id);
        if (deleteMovie == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();
        }
        movieRepository.deleteMovieById(id);
            return Response.ok("Movie deleted successfully").build();
        }






}// movie resource class ends
