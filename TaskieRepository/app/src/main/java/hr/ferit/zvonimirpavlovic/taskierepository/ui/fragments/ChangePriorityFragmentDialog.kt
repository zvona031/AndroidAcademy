package hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import hr.ferit.zvonimirpavlovic.taskierepository.R
import hr.ferit.zvonimirpavlovic.taskierepository.model.Priority
import hr.ferit.zvonimirpavlovic.taskierepository.model.Task
import hr.ferit.zvonimirpavlovic.taskierepository.persistence.TaskRoomRepository
import kotlinx.android.synthetic.main.fragment_dialog_change_priority.*

class ChangePriorityFragmentDialog: DialogFragment(){

    private var repository = TaskRoomRepository()
    private var priorityChangedListener: TaskChangedListener? = null
    lateinit var task: Task
    private var priority: Priority = Priority.LOW

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initUI()
    }

    private fun initUI() {
        val childCount = gradeChangeRadioGroup.childCount
        (0 until childCount).map { gradeChangeRadioGroup.getChildAt(it) as RadioButton }
            .filter { it.text.toString() == task.priority.toString() }
            .forEach { it.isChecked = true }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    private fun initListeners() {
        saveGrade.setOnClickListener {
            repository.changeTaskPriority(task,priority)

            priorityChangedListener!!.onPriorityChanged()

            dismiss()
        }
        gradeChangeRadioGroup.setOnCheckedChangeListener { _, id ->
            priority = when (id) {
                gradeChangeOneRadioBtn.id -> Priority.LOW
                gradeChangeTwoRadioBtn.id -> Priority.MEDIUM
                gradeChangeThreeRadioBtn.id -> Priority.HIGH
                else -> Priority.LOW
            }
        }

    }
    fun setPriorityChangedListener(gradeChangedListener: TaskChangedListener) {
        this.priorityChangedListener = gradeChangedListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_change_priority, container)
    }

    companion object {
        fun newInstance() = ChangePriorityFragmentDialog()
    }
}
