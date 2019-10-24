package hr.ferit.zvonimirpavlovic.movieapp.presentation

import hr.ferit.zvonimirpavlovic.movieapp.database.MoviesDatabase
import hr.ferit.zvonimirpavlovic.movieapp.model.Movie
import hr.ferit.zvonimirpavlovic.movieapp.ui.favorite_movies.FavoriteMoviesContract

class FavoriteMoviesPresenter(
    private val appDatabase: MoviesDatabase
) : FavoriteMoviesContract.Presenter {
    private lateinit var view: FavoriteMoviesContract.View

    override fun setView(view: FavoriteMoviesContract.View) {
        this.view = view
    }

    override fun onRequestFavoriteMovies() {
        if (appDatabase.moviesDao().getFavoriteMovies().isEmpty())
            view.onFavoriteMoviesEmpty()
        else{
            var favoriteMovies = appDatabase.moviesDao().getFavoriteMovies()
            favoriteMovies.forEach { it.isFavorite = true }
            view.onFavoriteMoviesRecieved(favoriteMovies)
        }

    }

    override fun onFavoriteClicked(movie: Movie) {
        appDatabase.moviesDao().deleteFavoriteMovie(movie)
        movie.isFavorite = !movie.isFavorite
        view.onFavoriteRemoved(movie)
    }

}
