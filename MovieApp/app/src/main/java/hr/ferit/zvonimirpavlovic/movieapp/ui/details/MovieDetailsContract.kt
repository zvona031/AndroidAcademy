package hr.ferit.zvonimirpavlovic.movieapp.ui.details

import hr.ferit.zvonimirpavlovic.movieapp.model.Review

interface MovieDetailsContract {

    interface View {

        fun onReviewsRecieved(reviews: List<Review>)

        fun onReviewsFailed(message: String)

    }

    interface Presenter {

        fun setView(view: MovieDetailsContract.View)

        fun onGetReviews(movieID: Int)

    }
}