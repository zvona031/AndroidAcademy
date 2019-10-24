package hr.ferit.zvonimirpavlovic.movieapp.ui.details

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_details.*
import hr.ferit.zvonimirpavlovic.movieapp.App
import hr.ferit.zvonimirpavlovic.movieapp.R
import hr.ferit.zvonimirpavlovic.movieapp.common.displayToast
import hr.ferit.zvonimirpavlovic.movieapp.common.loadImage
import hr.ferit.zvonimirpavlovic.movieapp.database.MoviesDatabase
import hr.ferit.zvonimirpavlovic.movieapp.model.Movie
import hr.ferit.zvonimirpavlovic.movieapp.model.Review
import hr.ferit.zvonimirpavlovic.movieapp.model.response.ReviewsResponse
import hr.ferit.zvonimirpavlovic.movieapp.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.movieapp.networking.interactors.MovieInteractor
import hr.ferit.zvonimirpavlovic.movieapp.presentation.DetailsPresenter
import hr.ferit.zvonimirpavlovic.movieapp.ui.adapters.ReviewAdapter
import hr.ferit.zvonimirpavlovic.movieapp.ui.base.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsFragment : BaseFragment(),MovieDetailsContract.View {

    private val reviewsAdapter by lazy { ReviewAdapter() }
    private val presenter : MovieDetailsContract.Presenter by lazy {
        DetailsPresenter(BackendFactory.getMovieInteractor())
    }
    companion object {

        private const val MOVIE_EXTRA = "movie_extra"
        fun getInstance(movie: Movie): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_EXTRA, movie)
            fragment.arguments = bundle
            return fragment
        }

    }
    private lateinit var movie: Movie

    override fun getLayoutResourceId(): Int = R.layout.fragment_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = arguments?.getParcelable(MOVIE_EXTRA) as Movie

        initUi()
        getReviews()
    }

    private fun initUi(){
        movieImage.loadImage(movie.poster)
        movieTitle.text = movie.title
        movieOverview.text = movie.overview
        movieReleaseDate.text = movie.releaseDate
        movieVoteAverage.text = movie.averageVote.toString()

        movieReviewList.apply {
            adapter = reviewsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }


    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    private fun getReviews(){
        presenter.onGetReviews(movie.id)
    }

    override fun onReviewsRecieved(reviews: List<Review>) {
        reviewsAdapter.setReviewList(reviews)
    }

    override fun onReviewsFailed(message: String) {
        activity?.displayToast(message)
    }
}