package org.example.demo5.db;

import jakarta.enterprise.context.ApplicationScoped;// Defines the scope of the class as application wide.
import jakarta.persistence.EntityManager; // entity manager to interact with database,CRUD operations
import jakarta.persistence.PersistenceContext;// inject em instance
import jakarta.transaction.Transactional;// all methods to do transaction with database
import org.example.demo5.model.Movie; // represents movie entity class,that represents table in db
import java.util.List;// used to fetch collection of entities

@ApplicationScoped // one instance of the class will exit in the whole application
@Transactional // all methods will exeute in db transaction

public class MovieRepository {  // class starts
    @PersistenceContext// to inject an em instance here
    private EntityManager em;

    public Movie CreateMovie(Movie movie) { // method to create movie  starts
        em.persist(movie);
        return movie;

    } // method ends

    /**
     * persist add the movie object in db
     * em track and make sure it is saved
     * return the save movie object
     */

    public List<Movie> getAllMovies() {
        return em.createNativeQuery("SELECT * FROM Movies", Movie.class).getResultList();
        //native SQL query(SELECT (*)rows FROM Movies table)
        //instructs JPA to map the results of the query to the movie entity class
        // each row of the results is converted to movie object.
    }

    public Movie getMovieById(Long id) {
        return em.find(Movie.class, id);
        //find method retrives entity from db using its primary key.syntax for find is given

    }

    public void updateMovie( Movie movie) {
        em.merge(movie);
        // updates the database record with the changes made to exiting movie
    }

    public void deleteMovieById(Long id) {
        Movie movieID = em.find(Movie.class, id);
        em.remove(movieID);
        // delete the movie record from db

    }
}// Movie Repository class ends








