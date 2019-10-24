package hr.ferit.zvonimirpavlovic.movieapp.ui.popular_movies

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
import hr.ferit.zvonimirpavlovic.movieapp.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.movieapp.presentation.PopularMoviesPresenter
import hr.ferit.zvonimirpavlovic.movieapp.ui.details.MoviesPagerFragment
import hr.ferit.zvonimirpavlovic.movieapp.ui.adapters.MoviesGridAdapter
import hr.ferit.zvonimirpavlovic.movieapp.ui.base.BaseFragment

class PopularMoviesFragment : BaseFragment(),PopularMoviesContract.View {
    private val SPAN_COUNT = 2

    private val gridAdapter by lazy {
        MoviesGridAdapter(
            { onMovieClicked(it) },
            { onFavoriteClicked(it) })
    }

    private val presenter: PopularMoviesContract.Presenter by lazy {
            PopularMoviesPresenter(BackendFactory.getMovieInteractor(),
            MoviesDatabase.getInstance(App.getAppContext()))
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_movie_grid

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesGrid.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
        requestPopularMovies()
    }
    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        requestPopularMovies()
    }

    private fun requestPopularMovies() {
        presenter.onRequestPopularMovies()
    }

    override fun onPopularMoviesRecieved(movies: List<Movie>) {
        gridAdapter.setMovies(movies)
    }

    override fun onPopularMoviesFailed(message: String) {
        activity?.displayToast(message)
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

    override fun onFavoriteAdded(title: String) {
        activity?.displayToast("$title is added to favorites!")
        gridAdapter.notifyDataSetChanged()
    }

    override fun onFavoriteRemoved(title: String) {
        activity?.displayToast("$title is removed from favorites!")
        gridAdapter.notifyDataSetChanged()
    }
}