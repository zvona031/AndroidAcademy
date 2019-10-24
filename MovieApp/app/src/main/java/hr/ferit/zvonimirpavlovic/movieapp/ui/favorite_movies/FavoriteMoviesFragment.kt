package hr.ferit.zvonimirpavlovic.movieapp.ui.favorite_movies

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_movie_grid.*
import hr.ferit.zvonimirpavlovic.movieapp.App

import hr.ferit.zvonimirpavlovic.movieapp.R
import hr.ferit.zvonimirpavlovic.movieapp.common.displayToast
import hr.ferit.zvonimirpavlovic.movieapp.common.showFragment
import hr.ferit.zvonimirpavlovic.movieapp.database.MoviesDatabase
import hr.ferit.zvonimirpavlovic.movieapp.model.Movie
import hr.ferit.zvonimirpavlovic.movieapp.presentation.FavoriteMoviesPresenter
import hr.ferit.zvonimirpavlovic.movieapp.ui.adapters.MoviesGridAdapter
import hr.ferit.zvonimirpavlovic.movieapp.ui.base.BaseFragment
import hr.ferit.zvonimirpavlovic.movieapp.ui.details.MoviesPagerFragment


class FavoriteMoviesFragment : BaseFragment(),FavoriteMoviesContract.View {

    private val SPAN_COUNT = 2

    private val gridAdapter by lazy {
        MoviesGridAdapter(
            { onMovieClicked(it) },
            { onFavoriteClicked(it) })
    }

    private val presenter: FavoriteMoviesContract.Presenter by lazy {
        FavoriteMoviesPresenter(
            MoviesDatabase.getInstance(App.getAppContext()) )
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_movie_grid

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesGrid.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
        presenter.setView(this)
        requestFavoriteMovies()
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        requestFavoriteMovies()
    }

    private fun requestFavoriteMovies() {
        presenter.onRequestFavoriteMovies()
    }

    override fun onFavoriteMoviesRecieved(movies: List<Movie>) {
        gridAdapter.setMovies(movies)
    }

    override fun onFavoriteMoviesEmpty() {
        activity?.displayToast("No favorite movies!")
    }

    private fun onMovieClicked(movie: Movie) {
        activity?.showFragment(
            R.id.mainFragmentHolder,
            MoviesPagerFragment.getInstance(
                ArrayList(gridAdapter.getMovies()),
                movie
            ),
            true
        )
    }

    private fun onFavoriteClicked(movie: Movie) {
        presenter.onFavoriteClicked(movie)
    }

    override fun onFavoriteRemoved(movie: Movie) {
        activity?.displayToast(movie.title + " is removed from favorites!")
        gridAdapter.removeMovie(movie)
    }
}
