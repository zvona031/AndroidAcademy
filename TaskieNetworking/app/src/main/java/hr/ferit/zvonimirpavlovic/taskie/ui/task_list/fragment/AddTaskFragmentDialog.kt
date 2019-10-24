package hr.ferit.zvonimirpavlovic.taskie.ui.task_list.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.*
import hr.ferit.zvonimirpavlovic.taskie.model.BackendPriorityTask
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.AddTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskPrefs
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRepository
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRoomRepository
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskFragmentDialog : DialogFragment() {

    private var taskAddedListener: TaskAddedListener? = null
    private val repository: TaskRepository = TaskRoomRepository()
    private val interactor = BackendFactory.getTaskieInteractor()

    interface TaskAddedListener {
        fun onTaskAdded(task: BackendTask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    fun setTaskAddedListener(listener: TaskAddedListener) {
        taskAddedListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_new_task, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi() {
        context?.let {
            prioritySelector.adapter = ArrayAdapter<BackendPriorityTask>(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                BackendPriorityTask.values()
            )
        }
        when (getLastPriority()) {
            LOW_STRING -> prioritySelector.setSelection(BackendPriorityTask.LOW.getValue() - 1)
            MEDIUM_STRING -> prioritySelector.setSelection(BackendPriorityTask.MEDIUM.getValue() - 1)
            HIGH_STRING -> prioritySelector.setSelection(BackendPriorityTask.HIGH.getValue() - 1)
        }
    }

    private fun initListeners() {
        saveTaskAction.setOnClickListener { saveTask() }
    }

    private fun saveTask() {
        if (isInputEmpty()) {
            context?.displayToast(getString(R.string.empty_fields))
            return
        }

        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val priority = prioritySelector.selectedItem as BackendPriorityTask
        interactor.save(AddTaskRequest(title, description, priority.getValue()), addTaskCallback(title,description,priority.getValue()))
        saveLastPriority()
        clearUi()
        dismiss()
    }

    private fun addTaskCallback(title: String,description: String, priority: Int): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>?, t: Throwable?) {
            handleOnFailure(title,description,priority)

        }

        override fun onResponse(call: Call<BackendTask>?, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()?.let { handleOkResponse(it) }
                }
            } else handleElseResponses(response.code())
        }
    }

    private fun handleElseResponses(code: Int) {
        activity?.displayToast(codeToMessage(code))
    }

    private fun handleOkResponse(task: BackendTask) {
        repository.addTask(toRoomTask(task))
        Log.d("TAGG",repository.getTaskById(task.id)?.sent.toString())
        taskAddedListener?.onTaskAdded(task)
    }

    private fun handleOnFailure(title: String,description: String, priority: Int) {
        var id = "0"
        if(repository.getTasks().isNotEmpty()){
            id = repository.getTasks().last().id + 1
        }
        val task = BackendTask(id= id  ,title = title,userId = "",content = description,taskPriority = priority,isFavorite = false,isCompleted = false,sent = false)
        repository.addTask(task)
        taskAddedListener?.onTaskAdded(task)
    }

    private fun saveLastPriority() {
        TaskPrefs.store(LAST_CHOOSED_PRIORITY, prioritySelector.selectedItem.toString())
    }

    private fun getLastPriority(): String? {
        return TaskPrefs.getString(LAST_CHOOSED_PRIORITY, LOW_STRING)
    }

    private fun clearUi() {
        newTaskTitleInput.text.clear()
        newTaskDescriptionInput.text.clear()
    }

    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(newTaskTitleInput.text) || TextUtils.isEmpty(
        newTaskDescriptionInput.text
    )

    companion object {
        fun newInstance(): AddTaskFragmentDialog {
            return AddTaskFragmentDialog()
        }
    }
}
