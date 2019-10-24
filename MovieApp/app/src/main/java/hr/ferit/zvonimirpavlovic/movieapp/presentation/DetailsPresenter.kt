package hr.ferit.zvonimirpavlovic.movieapp.presentation

import hr.ferit.zvonimirpavlovic.movieapp.model.response.ReviewsResponse
import hr.ferit.zvonimirpavlovic.movieapp.networking.interactors.MovieInteractor
import hr.ferit.zvonimirpavlovic.movieapp.ui.details.MovieDetailsContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsPresenter(private val interactor: MovieInteractor) : MovieDetailsContract.Presenter {

    private lateinit var view : MovieDetailsContract.View

    override fun setView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun onGetReviews(movieID: Int) {
        interactor.getReviewsForMovie(movieID, reviewsCallback())
    }

    private fun reviewsCallback(): Callback<ReviewsResponse> = object : Callback<ReviewsResponse>{
        override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
            view.onReviewsFailed(t.message!!)
        }

        override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
            if(response.isSuccessful)
                response.body()?.reviews?.let { view.onReviewsRecieved(it) }
        }

    }




}