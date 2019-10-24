package hr.ferit.zvonimirpavlovic.taskie.ui.task_list.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.*
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRoomRepository
import hr.ferit.zvonimirpavlovic.taskie.presentation.TasksFragmentPresenter
import hr.ferit.zvonimirpavlovic.taskie.ui.adapters.TaskAdapter
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseFragment
import hr.ferit.zvonimirpavlovic.taskie.ui.details.ContainerActivity
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.ext.android.inject

class TasksFragment : BaseFragment(),
    AddTaskFragmentDialog.TaskAddedListener, TasksFragmentContract.View {

    private val presenter by inject<TasksFragmentContract.Presenter> ()

    private val adapter by lazy { TaskAdapter { onItemSelected(it) } }

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearAllTasksMenuItem -> showClearAllDialog()
            R.id.sortTasksByPriority -> showSortByPriorityDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTaskAdded(task: BackendTask) {
        adapter.addData(task)
        checkList()
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    private fun initUi() {
        progress.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
        configurateRefreshColors()
        saveOfflineTasks()
        getAllTasks()
    }

    private fun saveOfflineTasks() {
        presenter.onSaveOfflineTasks()
    }

    private fun initListeners() {
        swipeContainer.setOnRefreshListener { onRefresh() }
        addTask.setOnClickListener { addTask() }
    }

    private fun configurateRefreshColors() {
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

    private fun getAllTasks() {
        presenter.onGetAllTasks()
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            showDeleteTaskDialog(adapter.getTaskByPosition(viewHolder.adapterPosition))
        }
    }

    private val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)

    private fun onRefresh() {
        swipeContainer.isRefreshing = true
        presenter.onSaveOfflineTasks()
        presenter.onGetAllTasks()
        swipeContainer.isRefreshing = false

    }

    private fun onItemSelected(task: BackendTask) {
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.id)
        }
        startActivity(detailsIntent)
    }

    private fun addTask() {
        val dialog =
            AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun checkList() {
        if (adapter.isEmpty()) {
            noData.visible()
        } else {
            noData.gone()
        }
    }

    private fun onDeleteTask(id: String) {
        presenter.onDeleteTask(id)
    }

    private fun onClearAllTasks() {
        adapter.getTasks().forEach {
            presenter.onDeleteTask(it.id)
        }
    }

    private fun showSortByPriorityDialog() {
        showYesNoDialog(
            activity,
            getString(R.string.sort_tasks_question),
            android.R.drawable.ic_dialog_alert
        ) { adapter.sortDataByPriority() }
    }

    private fun showClearAllDialog() {
        showYesNoDialog(
            activity,
            getString(R.string.clear_tasks_question),
            android.R.drawable.ic_dialog_alert
        ) { onClearAllTasks() }

    }

    private fun showDeleteTaskDialog(task: BackendTask) {
        showYesNoDialog(
            activity,
            getString(R.string.delete_task_question),
            android.R.drawable.ic_dialog_alert,
            { onDeleteTask(task.id) },
            { adapter.refresh() })
    }

    companion object {
        fun newInstance(): Fragment = TasksFragment()
    }

    override fun getAllTasksSuccessful(tasks: MutableList<BackendTask>) {
        adapter.addAll(tasks)
        checkList()
        progress.gone()
    }

    override fun getAllTasksFailed(repoTasks: MutableList<BackendTask>) {
        activity?.displayToast(getString(R.string.offline_mode_string))
        adapter.addAll(repoTasks)
        checkList()
        progress.gone()
    }

    override fun getAllTasksError(message: String) {
        activity?.displayToast(message)
        progress.gone()
    }

    override fun saveOfflineTaskSuccessful(oldID: String, task: BackendTask) {
        checkList()
    }

    override fun saveOfflineTaskFailed() {
        activity?.displayToast(getString(R.string.no_internet))
        adapter.refresh()
    }

    override fun saveOfflineTaskError(message: String) {
        activity?.displayToast(message)
        progress.gone()
    }

    override fun deleteTaskSuccessful(id: String) {
        val task = adapter.getTaskById(id)
        if (task != null) {
            adapter.deleteTask(task)
        }
        checkList()
    }

    override fun deleteTaskFailed() {
        activity?.displayToast(getString(R.string.no_internet))
        adapter.refresh()
    }

    override fun deleteTaskError(message: String) {
        activity?.displayToast(message)
        progress.gone()
    }
}

