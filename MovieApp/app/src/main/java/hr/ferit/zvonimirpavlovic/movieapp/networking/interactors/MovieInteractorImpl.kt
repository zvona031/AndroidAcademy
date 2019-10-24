package hr.ferit.zvonimirpavlovic.movieapp.networking.interactors

import hr.ferit.zvonimirpavlovic.movieapp.model.Movie
import hr.ferit.zvonimirpavlovic.movieapp.model.response.MoviesResponse
import hr.ferit.zvonimirpavlovic.movieapp.model.response.ReviewsResponse
import hr.ferit.zvonimirpavlovic.movieapp.networking.MovieApiService
import retrofit2.Callback

class MovieInteractorImpl(private val apiService: MovieApiService): MovieInteractor {

    override fun getPopularMovies(popularMoviesCallback: Callback<MoviesResponse>) {
        apiService.getPopularMovies().enqueue(popularMoviesCallback)
    }

    override fun getTopMovies(topMoviesCallback: Callback<MoviesResponse>) {
        apiService.getTopMovies().enqueue(topMoviesCallback)
    }

    override fun getMovie(movieId: Int, movieCallback: Callback<Movie>) {
        apiService.getMovie(movieId).enqueue(movieCallback)
    }

    override fun getReviewsForMovie(movieId: Int, callback: Callback<ReviewsResponse>) {
        apiService.getReviews(movieId).enqueue(callback)
    }
}