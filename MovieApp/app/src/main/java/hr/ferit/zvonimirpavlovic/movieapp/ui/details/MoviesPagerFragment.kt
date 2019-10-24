package hr.ferit.zvonimirpavlovic.movieapp.ui.details

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_pager.*
import hr.ferit.zvonimirpavlovic.movieapp.R
import hr.ferit.zvonimirpavlovic.movieapp.model.Movie
import hr.ferit.zvonimirpavlovic.movieapp.ui.adapters.MoviePagerAdapter
import hr.ferit.zvonimirpavlovic.movieapp.ui.base.BaseFragment

class MoviesPagerFragment : BaseFragment() {

    companion object {

        private const val PAGER_LIST_EXTRA = "list_extra"
        private const val PAGER_SELECTED_MOVIE_EXTRA = "selected_movie"
        fun getInstance(movieList: ArrayList<Movie>, selectedMovie: Movie): MoviesPagerFragment {
            val pagerFragment = MoviesPagerFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(PAGER_LIST_EXTRA, movieList)
            bundle.putParcelable(PAGER_SELECTED_MOVIE_EXTRA, selectedMovie)
            pagerFragment.arguments = bundle
            return pagerFragment
        }

    }
    private val movieList = mutableListOf<Movie>()

    private val pagerAdapter by lazy {
        MoviePagerAdapter(
            childFragmentManager
        )
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_pager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        movieList.addAll(arguments?.getParcelableArrayList(PAGER_LIST_EXTRA)!!)

        moviePager.adapter = pagerAdapter

        pagerAdapter.setMovies(movieList)

        moviePager.currentItem = arguments?.getParcelable<Movie>(
            PAGER_SELECTED_MOVIE_EXTRA)?.let { movieList.indexOf(it) }!!
    }

}