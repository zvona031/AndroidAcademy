package hr.ferit.zvonimirpavlovic.movieapp.ui.top_movies

import hr.ferit.zvonimirpavlovic.movieapp.model.Movie

interface TopMoviesContract {

    interface View {

        fun onTopMoviesRecieved(movies: List<Movie>)

        fun onTopMoviesFailed(message: String)

        fun onFavoriteAdded(title: String)

        fun onFavoriteRemoved(title: String)
    }

    interface Presenter {

        fun setView(view: TopMoviesContract.View)

        fun onRequestTopMovies()

        fun onFavoriteClicked(movie: Movie)

    }
}