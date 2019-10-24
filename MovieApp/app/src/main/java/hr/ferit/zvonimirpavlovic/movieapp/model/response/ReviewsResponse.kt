package hr.ferit.zvonimirpavlovic.movieapp.model.response

import com.google.gson.annotations.SerializedName
import hr.ferit.zvonimirpavlovic.movieapp.model.Review

data class ReviewsResponse(
    @SerializedName("results") val reviews : List<Review>
)