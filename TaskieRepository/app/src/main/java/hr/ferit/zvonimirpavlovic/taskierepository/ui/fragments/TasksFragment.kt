package hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.zvonimirpavlovic.taskierepository.R
import hr.ferit.zvonimirpavlovic.taskierepository.common.*
import hr.ferit.zvonimirpavlovic.taskierepository.model.Task
import hr.ferit.zvonimirpavlovic.taskierepository.persistence.TaskRoomRepository
import hr.ferit.zvonimirpavlovic.taskierepository.ui.activities.ContainerActivity
import hr.ferit.zvonimirpavlovic.taskierepository.ui.adapters.TaskAdapter
import hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener {

    private val repository = TaskRoomRepository()
    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        refreshTasks()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.clearAllTasksMenuItem -> showClearAllDialog()
            R.id.sortTasksByPriority -> showSortByPriorityDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        refreshTasks()
    }

    override fun onTaskAdded() {
        refreshTasks()
    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }

    private val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            showDeleteTaskDialog(adapter.getTaskByPosition(viewHolder.adapterPosition))
        }
    }

    private val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)

    private fun onItemSelected(task: Task){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.taskDbId)
        }
        startActivity(detailsIntent)
    }

    private fun refreshTasks() {
        progress.gone()
        val data = repository.getTasks()
        if (data.isNotEmpty()) {
            noData.gone()
        } else {
            noData.visible()
        }
        adapter.setData(data)
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun deleteTask(task: Task){
        repository.deleteTask(task)
        refreshTasks()
    }

    private fun clearAllTasks() {
        repository.clearAllTasks()
        refreshTasks()
    }

    private fun showSortByPriorityDialog() {
        showDialog(activity,getString(R.string.sort_tasks_question),android.R.drawable.ic_dialog_alert,android.R.string.yes,{adapter.sortDataByPriority()},android.R.string.no)
    }

    private fun showClearAllDialog() {
        showDialog(activity,getString(R.string.clear_tasks_question),android.R.drawable.ic_dialog_alert,android.R.string.yes,{clearAllTasks()},android.R.string.no)

    }

    private fun showDeleteTaskDialog(task:Task){
        showDialog(activity,getString(R.string.delete_task_question),android.R.drawable.ic_dialog_alert,android.R.string.yes,{deleteTask(task)},android.R.string.no,{refreshTasks()})
    }

    companion object {
        fun newInstance(): Fragment = TasksFragment()
    }
}

