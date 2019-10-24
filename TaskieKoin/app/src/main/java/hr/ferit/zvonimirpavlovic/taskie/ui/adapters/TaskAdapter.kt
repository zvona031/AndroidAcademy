package hr.ferit.zvonimirpavlovic.taskie.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask


class TaskAdapter(private val onItemSelected: (BackendTask) -> Unit) : RecyclerView.Adapter<TaskHolder>() {

    private val data: MutableList<BackendTask> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskHolder(v)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bindData(data[position], onItemSelected)
    }

    fun isEmpty(): Boolean {
        return data.isEmpty()
    }

    fun addAll(data: List<BackendTask>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun sortDataByPriority() {
        data.sortBy { it.taskPriority }
        data.reverse()
        notifyDataSetChanged()
    }

    fun getTaskByPosition(position: Int): BackendTask {
        return data[position]
    }

    fun getTaskById(id: String): BackendTask? {
        return data.find { it.id == id }
    }

    fun addData(item: BackendTask) {
        data.add(item)
        notifyDataSetChanged()
    }

    fun refresh() {
        notifyDataSetChanged()
    }

    fun deleteTask(task: BackendTask) {
        data.remove(task)
        notifyDataSetChanged()
    }

    fun getTasks(): List<BackendTask> = data

}

