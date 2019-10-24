package hr.ferit.zvonimirpavlovic.movieapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import hr.ferit.zvonimirpavlovic.movieapp.model.Movie

@Dao
interface MoviesDao {

    @Insert(onConflict = REPLACE)
    fun addFavoriteMovie(movie: Movie)

    @Delete
    fun deleteFavoriteMovie(movie: Movie)

    @Query("SELECT * FROM Movie")
    fun getFavoriteMovies(): List<Movie>

}