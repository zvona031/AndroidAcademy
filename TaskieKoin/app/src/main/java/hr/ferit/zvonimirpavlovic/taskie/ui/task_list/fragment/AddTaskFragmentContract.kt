package hr.ferit.zvonimirpavlovic.taskie.ui.task_list.fragment;

import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.AddTaskRequest

interface AddTaskFragmentContract {

    interface View {

        fun saveTaskSuccesful(task: BackendTask)

        fun saveTaskFailed(task: BackendTask)

        fun saveTaskError(message: String)

    }

    interface Presenter {

        fun setView(view: AddTaskFragmentContract.View)

        fun saveTask(taskRequest: AddTaskRequest)

    }

}