package hr.ferit.zvonimirpavlovic.movieapp.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*
import hr.ferit.zvonimirpavlovic.movieapp.R
import hr.ferit.zvonimirpavlovic.movieapp.common.loadImage
import hr.ferit.zvonimirpavlovic.movieapp.model.Movie

class MoviesGridViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindItem(movie: Movie, onMovieClickListener: (Movie) -> Unit, onFavoriteClickListener: (Movie) -> Unit) {
        movieImage.loadImage(movie.poster)
        movieFavorite.setImageResource(if (movie.isFavorite) R.drawable.ic_favorite_full_red else R.drawable.ic_favorite_border_red)

        containerView.setOnClickListener {
            onMovieClickListener(movie)
        }

        movieFavorite.setOnClickListener {
            onFavoriteClickListener(movie)
        }
    }

}