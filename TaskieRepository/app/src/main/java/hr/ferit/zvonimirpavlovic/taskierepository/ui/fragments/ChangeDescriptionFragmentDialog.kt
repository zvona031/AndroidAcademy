package hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import hr.ferit.zvonimirpavlovic.taskierepository.R
import hr.ferit.zvonimirpavlovic.taskierepository.common.EMPTY_STRING
import hr.ferit.zvonimirpavlovic.taskierepository.model.Task
import hr.ferit.zvonimirpavlovic.taskierepository.persistence.TaskRoomRepository
import kotlinx.android.synthetic.main.fragment_dialog_change_description.*

class ChangeDescriptionFragmentDialog: DialogFragment() {

    private var repository = TaskRoomRepository()
    private var descriptionChangedListener: TaskChangedListener? = null
    lateinit var task: Task
    private var description: String = EMPTY_STRING

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_change_description, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    private fun initListeners() {
        saveDescription.setOnClickListener {
            description = etDescription.text.toString()
            repository.changeTaskDescription(task,description)
            descriptionChangedListener!!.onDescriptionChanged()
            dismiss()
        }
    }

    fun setDescriptionChangedListener(titleChangedListener: TaskChangedListener){
        this.descriptionChangedListener = titleChangedListener
    }

    companion object {
        fun newInstance() = ChangeDescriptionFragmentDialog()
    }
}