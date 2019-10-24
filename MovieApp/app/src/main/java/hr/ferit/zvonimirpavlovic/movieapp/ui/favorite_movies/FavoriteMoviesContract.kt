package hr.ferit.zvonimirpavlovic.movieapp.ui.favorite_movies

import hr.ferit.zvonimirpavlovic.movieapp.model.Movie

interface FavoriteMoviesContract {

    interface View {

        fun onFavoriteMoviesRecieved(movies: List<Movie>)

        fun onFavoriteMoviesEmpty()

        fun onFavoriteRemoved(movie: Movie)

    }

    interface Presenter {

        fun setView(view: FavoriteMoviesContract.View)

        fun onRequestFavoriteMovies()

        fun onFavoriteClicked(movie: Movie)

    }
}