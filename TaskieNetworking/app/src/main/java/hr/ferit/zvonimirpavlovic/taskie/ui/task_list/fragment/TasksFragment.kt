package hr.ferit.zvonimirpavlovic.taskie.ui.task_list.fragment

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
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.*
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.AddTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.DeleteResponse
import hr.ferit.zvonimirpavlovic.taskie.model.response.GetTasksResponse
import hr.ferit.zvonimirpavlovic.taskie.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRepository
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRoomRepository
import hr.ferit.zvonimirpavlovic.taskie.ui.details.ContainerActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.adapters.TaskAdapter
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksFragment : BaseFragment(),
    AddTaskFragmentDialog.TaskAddedListener {

    private val repository: TaskRepository = TaskRoomRepository()
    private val adapter by lazy { TaskAdapter { onItemSelected(it) } }
    private val interactor = BackendFactory.getTaskieInteractor()

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
        repository.getTasks().forEach { if(it.sent == false){
            interactor.save(AddTaskRequest(it.title,it.content,it.taskPriority),addOfflineTaskCallback(it.id))
        }
        }

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
        interactor.getTasks(getTaskieCallback())
    }

    private fun getTaskieCallback(): Callback<GetTasksResponse> = object : Callback<GetTasksResponse> {
        override fun onFailure(call: Call<GetTasksResponse>?, t: Throwable?) {
            handleOfflineTasks()
        }

        override fun onResponse(call: Call<GetTasksResponse>?, response: Response<GetTasksResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()!!.notes?.let { handleOkResponse(it) }
                }
            } else handleElseResponses(response.code())
        }
    }

    private fun handleOfflineTasks() {
        activity?.displayToast(getString(R.string.offline_mode_string))
        adapter.addAll(repository.getTasks())
        checkList()
        progress.gone()
    }

    private fun handleOkResponse(response: MutableList<BackendTask>) {
        for (backendTask in response) {
            if (repository.getTaskById(backendTask.id) == null){
                repository.addTask(toRoomTask(backendTask))
            }
        }
        adapter.addAll(response)
        checkList()
        progress.gone()
    }

    private fun addOfflineTaskCallback(oldId: String): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>?, t: Throwable?) {
            handleOnFailure()

        }

        override fun onResponse(call: Call<BackendTask>?, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()?.let { handleOkOfflineAddedResponse(oldId,it) }
                }
            } else handleElseResponses(response.code())
        }
    }

    private fun handleOkOfflineAddedResponse(oldId: String,task: BackendTask) {
        repository.getTaskById(oldId)?.let {
            repository.deleteTask(it) }
        checkList()
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
        saveOfflineTasks()
        getAllTasks()
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
        interactor.delete(id, getDeleteCallback(id))
    }

    private fun getDeleteCallback(id: String): Callback<DeleteResponse> = object : Callback<DeleteResponse> {
        override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
            handleOnFailure()
        }

        override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkDeleteResponse(id)
                }
            } else handleElseResponses(response.code())
        }
    }

    private fun handleOnFailure() {
        activity?.displayToast(getString(R.string.no_internet))
        adapter.refresh()
    }

    private fun handleOkDeleteResponse(id: String) {
        val task = adapter.getTaskById(id)
        if (task != null) {
            adapter.deleteTask(task)
            repository.deleteTask(task)
        }
        checkList()
    }


    private fun handleElseResponses(code: Int) {
        activity?.displayToast(codeToMessage(code))
        progress.gone()
    }

    private fun onClearAllTasks() {
        adapter.getTasks().forEach {
            interactor.delete(it.id, getDeleteCallback(it.id))
        }
    }

    override fun onStart() {
        super.onStart()
        saveOfflineTasks()
        getAllTasks()
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
}

