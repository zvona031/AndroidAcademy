package hr.ferit.zvonimirpavlovic.movieapp.model

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String
)