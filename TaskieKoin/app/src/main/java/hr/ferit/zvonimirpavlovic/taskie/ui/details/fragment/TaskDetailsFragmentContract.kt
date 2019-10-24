package hr.ferit.zvonimirpavlovic.taskie.ui.details.fragment;

import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask

interface TaskDetailsFragmentContract {

    interface View {

        fun tryDisplayTaskSuccessful(task: BackendTask)

        fun tryDisplayTaskError(message: String)

        fun tryDisplayTaskFailed()
    }

    interface Presenter {

        fun setView(view: TaskDetailsFragmentContract.View)

        fun onTryDisplayTask(taskID: String)

        fun getTaskById(id: String): BackendTask?
    }

}