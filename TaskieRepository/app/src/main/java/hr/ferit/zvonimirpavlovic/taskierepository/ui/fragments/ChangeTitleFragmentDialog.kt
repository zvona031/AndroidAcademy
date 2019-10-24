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
import kotlinx.android.synthetic.main.fragment_dialog_change_title.*

class ChangeTitleFragmentDialog : DialogFragment(){

    private var repository = TaskRoomRepository()
    private var titleChangedListener: TaskChangedListener? = null
    lateinit var task: Task
    private var title: String = EMPTY_STRING

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_change_title, container)
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
        saveTitle.setOnClickListener {
            title = etTitle.text.toString()
            repository.changeTaskTitle(task,title)
            titleChangedListener!!.onTitleChanged()
            dismiss()
        }
    }

    fun setTitleChangedListener(titleChangedListener: TaskChangedListener){
        this.titleChangedListener = titleChangedListener
    }

    companion object {
        fun newInstance() = ChangeTitleFragmentDialog()
    }
}
