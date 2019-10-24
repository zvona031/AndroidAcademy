package hr.ferit.zvonimirpavlovic.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.zvonimirpavlovic.movieapp.R
import hr.ferit.zvonimirpavlovic.movieapp.model.Movie

class MoviesGridAdapter(private val onMovieClickListener: (Movie) -> Unit, private val onFavoriteClickListener:(Movie) -> Unit) : RecyclerView.Adapter<MoviesGridViewHolder>(){

    private val movies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesGridViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MoviesGridViewHolder, position: Int) {
        holder.bindItem(movies[position], onMovieClickListener, onFavoriteClickListener)
    }

    fun setMovies(movies: List<Movie>){
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun getMovies(): List<Movie> = movies

    fun removeMovie(movie: Movie) {
        movies.remove(movie)
        notifyDataSetChanged()
    }


}