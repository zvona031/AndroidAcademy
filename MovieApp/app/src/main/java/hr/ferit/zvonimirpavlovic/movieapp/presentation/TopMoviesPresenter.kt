package hr.ferit.zvonimirpavlovic.movieapp.presentation

import hr.ferit.zvonimirpavlovic.movieapp.database.MoviesDatabase
import hr.ferit.zvonimirpavlovic.movieapp.model.Movie
import hr.ferit.zvonimirpavlovic.movieapp.model.response.MoviesResponse
import hr.ferit.zvonimirpavlovic.movieapp.networking.interactors.MovieInteractor
import hr.ferit.zvonimirpavlovic.movieapp.ui.top_movies.TopMoviesContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopMoviesPresenter(
    private val interactor: MovieInteractor,
    private val appDatabase: MoviesDatabase
): TopMoviesContract.Presenter {

    private lateinit var view: TopMoviesContract.View

    override fun setView(view: TopMoviesContract.View) {
        this.view = view
    }

    override fun onRequestTopMovies() {
        interactor.getTopMovies(topMoviesCallback())
    }

    private fun topMoviesCallback(): Callback<MoviesResponse> =
        object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                t.message?.let { view.onTopMoviesFailed(it) }
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val favoriteMovies = appDatabase.moviesDao().getFavoriteMovies()
                    response.body()?.movies?.forEach {
                        if (favoriteMovies.contains(it))
                            it.isFavorite = true
                    }
                    response.body()?.movies?.let { view.onTopMoviesRecieved(it) }
                }
            }

        }

    override fun onFavoriteClicked(movie: Movie) {
        if(movie.isFavorite){
            appDatabase.moviesDao().deleteFavoriteMovie(movie)
            movie.isFavorite = !movie.isFavorite
            view.onFavoriteRemoved(movie.title)
        }
        else {
            movie.isFavorite = !movie.isFavorite
            appDatabase.moviesDao().addFavoriteMovie(movie)
            view.onFavoriteAdded(movie.title)
        }
    }
}