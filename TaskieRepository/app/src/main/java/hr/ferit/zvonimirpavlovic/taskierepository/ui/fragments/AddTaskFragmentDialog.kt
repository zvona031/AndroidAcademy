package hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hr.ferit.zvonimirpavlovic.taskierepository.R
import hr.ferit.zvonimirpavlovic.taskierepository.common.*
import hr.ferit.zvonimirpavlovic.taskierepository.model.Priority
import hr.ferit.zvonimirpavlovic.taskierepository.model.Task
import hr.ferit.zvonimirpavlovic.taskierepository.persistence.TaskPrefs
import hr.ferit.zvonimirpavlovic.taskierepository.persistence.TaskRepository
import hr.ferit.zvonimirpavlovic.taskierepository.persistence.TaskRoomRepository
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*

class AddTaskFragmentDialog: DialogFragment() {

    private var taskAddedListener: TaskAddedListener? = null
    private val repository:TaskRepository = TaskRoomRepository()

    interface TaskAddedListener{
        fun onTaskAdded()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }
    fun setTaskAddedListener(listener: TaskAddedListener){
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

    private fun initUi(){
        context?.let {
            prioritySelector.adapter = ArrayAdapter<Priority>(it, android.R.layout.simple_spinner_dropdown_item, Priority.values())
        }
        when(getLastPriority()){
            LOW_STRING -> prioritySelector.setSelection(Priority.LOW.getValue())
            MEDIUM_STRING -> prioritySelector.setSelection(Priority.MEDIUM.getValue())
            HIGH_STRING -> prioritySelector.setSelection(Priority.HIGH.getValue())
        }
    }

    private fun initListeners(){
        saveTaskAction.setOnClickListener{ saveTask() }
    }

    private fun saveTask() {
        if (isInputEmpty()){
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val priority = prioritySelector.selectedItem as Priority

        repository.addTask(
            Task(
                title = title,
                description = description,
                priority = priority
            )
        )

        saveLastPriority()
        clearUi()
        taskAddedListener?.onTaskAdded()
        dismiss()
    }

    private fun saveLastPriority() {
        TaskPrefs.store(LAST_CHOOSED_PRIORITY,prioritySelector.selectedItem.toString())
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

    companion object{
        fun newInstance(): AddTaskFragmentDialog {
            return AddTaskFragmentDialog()
        }
    }
}