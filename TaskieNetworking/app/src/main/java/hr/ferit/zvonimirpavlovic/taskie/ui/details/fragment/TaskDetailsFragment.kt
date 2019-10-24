package hr.ferit.zvonimirpavlovic.taskie.ui.details.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.*
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRoomRepository
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseFragment
import hr.ferit.zvonimirpavlovic.taskie.ui.details.*
import kotlinx.android.synthetic.main.fragment_task_details.*
import kotlinx.android.synthetic.main.fragment_tasks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TaskDetailsFragment : BaseFragment(),
    TaskUpdatedListener {

    private val repository = TaskRoomRepository()
    private val interactor = BackendFactory.getTaskieInteractor()
    private var taskID = NO_TASK

    override fun getLayoutResourceId(): Int = R.layout.fragment_task_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editTaskMenuItem -> repository.getTaskById(taskID)?.let { openUpdateTaskDialog(it) }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTaskUpdated() {
        detailsTaskTitle.text = repository.getTaskById(taskID)?.title
        detailsTaskDescription.text = repository.getTaskById(taskID)?.content
        repository.getTaskById(taskID)?.taskPriority?.let { toPriority(it).getColor() }?.let {
            detailsPriorityView.setBackgroundResource(
                it
            )
        }
    }

    private fun openUpdateTaskDialog(task: BackendTask) {
        val dialog = UpdateTaskFragmentDialog.newInstance()
        dialog.task = task
        dialog.setTaskUpdatedListener(this)
        dialog.show(childFragmentManager, dialog.tag)

    }

    private fun tryDisplayTask(id: String) {
        interactor.getTaskById(id, getTaskByIdCallback())
    }


    private fun getTaskByIdCallback(): Callback<BackendTask> = object : Callback<BackendTask>{
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {
            handleOnFailure()
        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> displayTask(response.body())
                }
            }
            else handleElseResponses(response.code())
        }
    }

    private fun handleElseResponses(code: Int) {
        activity?.displayToast(codeToMessage(code))
        progress.gone()
    }

    private fun handleOnFailure() {
        activity?.displayToast(getString(R.string.no_internet))
    }

    private fun displayTask(task: BackendTask?) {
        if( task != null) {
            detailsTaskTitle.text = task.title
            detailsTaskDescription.text = task.content
            detailsPriorityView.setBackgroundResource(toPriority(task.taskPriority).getColor())
        }
    }

    companion object {
        fun newInstance(taskId: String): TaskDetailsFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment()
                .apply { arguments = bundle }
        }
    }
}
