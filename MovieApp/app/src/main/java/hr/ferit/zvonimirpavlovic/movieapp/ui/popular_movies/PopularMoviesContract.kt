package hr.ferit.zvonimirpavlovic.movieapp.ui.popular_movies

import hr.ferit.zvonimirpavlovic.movieapp.model.Movie

interface PopularMoviesContract {

    interface View {

        fun onPopularMoviesRecieved(movies: List<Movie>)

        fun onPopularMoviesFailed(message: String)

        fun onFavoriteAdded(title: String)

        fun onFavoriteRemoved(title: String)

    }

    interface Presenter {

        fun setView(view: PopularMoviesContract.View)

        fun onRequestPopularMovies()

        fun onFavoriteClicked(movie: Movie)

    }
}