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

    /**
     * GET method to get all list of movies present in db
     * call getAllMovies method from MovieRepository
     * produces response in json format
     * @return response ok if all movies get display
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMovies() {  // getAllMovies method starts
        List<Movie> movies = movieRepository.getAllMovies();
        return Response.ok(movies).build();


    } // get all movies method ends

    /**
     * GET method to get movie by specific ID
     * consumes and produces response in json format
     * @param id search for specific id
     *
     * @return if movie found with id ,return response code ok,else return response status NOT FOUND.
     */
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("id") Long id) { // method starts
        Movie movieid = movieRepository.getMovieById(id);
        if (movieid != null) {
            return Response.ok(movieid).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }// method ends


    /**
     * POST method to add new movies
     * consumes response in json , produces in text format
     * calls createmovie method through movieRepository
     * @param movie is passed
     * @return response code ok if movie gets created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.TEXT_PLAIN})
    public Response CreateMovie(Movie movie) { // method starts
        movieRepository.CreateMovie(movie);
        return Response.status(Response.Status.CREATED)
                .entity("CreatedMovie")
                .build();

    } // method ends

    /**
     * PUT method to update the record for movie.
     * @param id search for specific id to update the movie
     * if id found which is to be updated, set new values for attributes,
     * @return response code ok with movie updated, else movie NOT FOUND
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("id") Long id,Movie updatedMovie) { // method starts
        Movie existingmovie = movieRepository.getMovieById(id);
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


    }// method ends

    /**
     * Delete method to delete movie by specific id.
     * call the getMOvieById method through movie respository class
     * @param id search for specific id , which is to be delected
     * @return if id not found in db , return response code NOT FOUND,else movie found by id,response code ok.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id") Long id) { // method starts
        Movie deleteMovie = movieRepository.getMovieById(id);
        if (deleteMovie == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();
        }
        movieRepository.deleteMovieById(id);
            return Response.ok("Movie deleted successfully").build();
        } // method ends






}// movie resource class ends
