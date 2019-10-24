package hr.ferit.zvonimirpavlovic.movieapp.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_review.*
import hr.ferit.zvonimirpavlovic.movieapp.model.Review

class ReviewViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(review: Review) {

        reviewAuthor.text = review.author
        reviewContent.text = review.content

    }

}