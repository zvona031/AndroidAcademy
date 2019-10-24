package hr.ferit.zvonimirpavlovic.movieapp.model.response

import com.google.gson.annotations.SerializedName
import hr.ferit.zvonimirpavlovic.movieapp.model.Movie

class MoviesResponse(@SerializedName("results") val movies: ArrayList<Movie>)