package hr.ferit.zvonimirpavlovic.taskie.ui.details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.RESPONSE_OK
import hr.ferit.zvonimirpavlovic.taskie.common.displayToast
import hr.ferit.zvonimirpavlovic.taskie.common.priorityFactory
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.EditTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.EditTaskResponse
import hr.ferit.zvonimirpavlovic.taskie.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRoomRepository
import hr.ferit.zvonimirpavlovic.taskie.presentation.UpdateTaskFragmentPresenter
import hr.ferit.zvonimirpavlovic.taskie.ui.details.TaskUpdatedListener
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateTaskFragmentDialog : DialogFragment(),UpdateTaskFragmentContract.View {

    private val presenter by inject<UpdateTaskFragmentContract.Presenter>()

    private var taskUpdateListener: TaskUpdatedListener? = null
    lateinit var task: BackendTask
    private var priority = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_new_task, container)
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }


    private fun initUI() {
        newTaskHeading.text = getString(R.string.heading_edit_task)
        newTaskDescriptionInput.setText(task.content)
        newTaskTitleInput.setText(task.title)
        prioritySelector.setSelection(task.taskPriority - 1)
    }

    fun setTaskUpdatedListener(listener: TaskUpdatedListener) {
        taskUpdateListener = listener
    }


    private fun initListeners() {
        saveTaskAction.setOnClickListener {
            priority = prioritySelector.priorityFactory().getValue()
            presenter.onEditTask(EditTaskRequest(
                task.id,
                newTaskTitleInput.text.toString(),
                newTaskDescriptionInput.text.toString(),
                priority))

        }
    }

    override fun editTaskSuccessful() {
        taskUpdateListener?.onTaskUpdated()
        dismiss()
    }

    override fun editTaskFailed() {
        activity?.displayToast(getString(R.string.no_internet))
    }

    override fun editTaskError(message: String) {
        activity?.displayToast(message)
    }

    companion object {
        fun newInstance() = UpdateTaskFragmentDialog()
    }
}