package hr.ferit.zvonimirpavlovic.taskie.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.*
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.EditTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.EditTaskResponse
import hr.ferit.zvonimirpavlovic.taskie.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRoomRepository
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateTaskFragmentDialog : DialogFragment() {

    private val interactor = BackendFactory.getTaskieInteractor()
    private var repository = TaskRoomRepository()
    private var taskUpdateListener: TaskUpdatedListener? = null
    lateinit var task: BackendTask
    private var priority = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_new_task, container)
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
            interactor.edit(
                EditTaskRequest(
                    task.id,
                    newTaskTitleInput.text.toString(),
                    newTaskDescriptionInput.text.toString(),
                    priority
                ), editTaskCallback()
            )
        }
    }

    private fun editTaskCallback(): Callback<EditTaskResponse> = object : Callback<EditTaskResponse> {
        override fun onFailure(call: Call<EditTaskResponse>, t: Throwable) {
            handleOnFailure()
        }

        override fun onResponse(call: Call<EditTaskResponse>, response: Response<EditTaskResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()?.let { handleOkResponse(it) }
                }
            }
        }

    }

    private fun handleOkResponse(it: EditTaskResponse) {
        repository.updateTask(
            task.id,
            newTaskTitleInput.text.toString(),
            newTaskDescriptionInput.text.toString(),
            priority
        )
        activity?.displayToast(it.message)
        taskUpdateListener?.onTaskUpdated()
        dismiss()
    }

    private fun handleOnFailure() {
        activity?.displayToast(getString(R.string.no_internet))
    }

    companion object {
        fun newInstance() = UpdateTaskFragmentDialog()
    }
}