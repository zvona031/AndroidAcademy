package hr.ferit.zvonimirpavlovic.taskie.ui.details.fragment;

import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.EditTaskRequest

interface UpdateTaskFragmentContract {

    interface View {

        fun editTaskSuccessful()

        fun editTaskFailed()

        fun editTaskError(message: String)

    }

    interface Presenter {

        fun setView(view: UpdateTaskFragmentContract.View)

        fun onEditTask(editRequest: EditTaskRequest)

    }

}