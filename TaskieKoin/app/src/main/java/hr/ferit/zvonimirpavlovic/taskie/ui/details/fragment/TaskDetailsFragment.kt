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
import hr.ferit.zvonimirpavlovic.taskie.presentation.TaskDetailsFragmentPresenter
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseFragment
import hr.ferit.zvonimirpavlovic.taskie.ui.details.TaskUpdatedListener
import kotlinx.android.synthetic.main.fragment_task_details.*
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TaskDetailsFragment : BaseFragment(),
    TaskUpdatedListener,TaskDetailsFragmentContract.View {

    private val presenter by inject<TaskDetailsFragmentContract.Presenter>()

    private var taskID = NO_TASK

    override fun getLayoutResourceId(): Int = R.layout.fragment_task_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editTaskMenuItem -> presenter.getTaskById(taskID).let {
                if (it != null) {
                    openUpdateTaskDialog(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTaskUpdated() {
        val task = presenter.getTaskById(taskID)
        detailsTaskTitle.text = task?.title
        detailsTaskDescription.text = task?.content
        task?.taskPriority?.let { toPriority(it)?.getColor() }?.let { detailsPriorityView.setBackgroundResource(it) }
    }

    private fun openUpdateTaskDialog(task: BackendTask) {
        val dialog = UpdateTaskFragmentDialog.newInstance()
        dialog.task = task
        dialog.setTaskUpdatedListener(this)
        dialog.show(childFragmentManager, dialog.tag)

    }

    private fun tryDisplayTask(id: String) {
        presenter.onTryDisplayTask(id)
    }

    override fun tryDisplayTaskSuccessful(task: BackendTask) {
        detailsTaskTitle.text = task.title
        detailsTaskDescription.text = task.content
        detailsPriorityView.setBackgroundResource(toPriority(task.taskPriority).getColor())
    }

    override fun tryDisplayTaskError(message: String) {
        activity?.displayToast(message)
        progress.gone()
    }

    override fun tryDisplayTaskFailed() {
        activity?.displayToast(getString(R.string.no_internet))
    }

    companion object {
        fun newInstance(taskId: String): TaskDetailsFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment()
                .apply { arguments = bundle }
        }
    }
}
