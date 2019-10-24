package hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments

import android.os.Bundle
import android.view.View
import hr.ferit.zvonimirpavlovic.taskierepository.R
import hr.ferit.zvonimirpavlovic.taskierepository.common.EXTRA_TASK_ID
import hr.ferit.zvonimirpavlovic.taskierepository.common.NO_TASK
import hr.ferit.zvonimirpavlovic.taskierepository.common.displayToast
import hr.ferit.zvonimirpavlovic.taskierepository.model.Task
import hr.ferit.zvonimirpavlovic.taskierepository.persistence.TaskRoomRepository
import hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*



class TaskDetailsFragment : BaseFragment(),TaskChangedListener {
    private val repository = TaskRoomRepository()

    private var taskID = NO_TASK

    override fun getLayoutResourceId(): Int = R.layout.fragment_task_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getLong(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        setupListeners()
    }

    override fun onTitleChanged() {
        detailsTaskTitle.text = repository.getTaskById(taskID).title
    }

    override fun onDescriptionChanged() {
        detailsTaskDescription.text = repository.getTaskById(taskID).description
    }

    override fun onPriorityChanged() {
        detailsPriorityView.setBackgroundResource(repository.getTaskById(taskID).priority.getColor())
    }

    private fun setupListeners() {
        detailsPriorityView.setOnClickListener { openChangePriorityDialog(repository.getTaskById(taskID)) }
        detailsTaskTitle.setOnClickListener { openChangeTitleDialog(repository.getTaskById(taskID)) }
        detailsTaskDescription.setOnClickListener { openChangeDescriptionDialog(repository.getTaskById(taskID)) }
    }

    private fun openChangePriorityDialog(task: Task) {
        val dialog = ChangePriorityFragmentDialog.newInstance()
        dialog.task = task
        dialog.setPriorityChangedListener(this)
        dialog.show(childFragmentManager,dialog.tag)
    }

    private fun openChangeTitleDialog(task: Task){
        val dialog = ChangeTitleFragmentDialog.newInstance()
        dialog.task = task
        dialog.setTitleChangedListener(this)
        dialog.show(childFragmentManager,dialog.tag)
    }

    private fun openChangeDescriptionDialog(task: Task) {
        val dialog = ChangeDescriptionFragmentDialog.newInstance()
        dialog.task = task
        dialog.setDescriptionChangedListener(this)
        dialog.show(childFragmentManager,dialog.tag)
    }

    private fun tryDisplayTask(id: Long) {
        try {
            val task = repository.getTaskById(id)
            displayTask(task)
        } catch (e: NoSuchElementException) {
            context?.displayToast(getString(R.string.noTaskFound))
        }
    }

    private fun displayTask(task: Task) {
        detailsTaskTitle.text = task.title
        detailsTaskDescription.text = task.description
        detailsPriorityView.setBackgroundResource(task.priority.getColor())
    }

    companion object {
        fun newInstance(taskId: Long): TaskDetailsFragment {
            val bundle = Bundle().apply { putLong(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
