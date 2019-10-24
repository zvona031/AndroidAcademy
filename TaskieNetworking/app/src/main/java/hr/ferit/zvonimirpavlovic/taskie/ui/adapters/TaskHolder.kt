package hr.ferit.zvonimirpavlovic.taskie.ui.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.zvonimirpavlovic.taskie.common.toPriority
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.view.*

class TaskHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(task: BackendTask, onItemSelected: (BackendTask) -> Unit) {

        containerView.setOnClickListener { onItemSelected(task) }
        containerView.taskTitle.text = task.title

        val drawable = containerView.taskPriority.drawable
        val wrapDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(
            wrapDrawable,
            ContextCompat.getColor(containerView.context, toPriority(task.taskPriority).getColor())
        )
    }
}

