package hr.ferit.zvonimirpavlovic.taskie.presentation

import hr.ferit.zvonimirpavlovic.taskie.common.RESPONSE_OK
import hr.ferit.zvonimirpavlovic.taskie.common.codeToMessage
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.EditTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.EditTaskResponse
import hr.ferit.zvonimirpavlovic.taskie.networking.interactor.TaskieInteractor
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRepository
import hr.ferit.zvonimirpavlovic.taskie.ui.details.fragment.UpdateTaskFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateTaskFragmentPresenter(
    private val interactor: TaskieInteractor,
    private val repository: TaskRepository
) : UpdateTaskFragmentContract.Presenter {

    private lateinit var view: UpdateTaskFragmentContract.View

    override fun setView(view: UpdateTaskFragmentContract.View) {
        this.view = view
    }

    override fun onEditTask(editRequest: EditTaskRequest) {
        interactor.edit(editRequest,editTaskCallback(editRequest))
    }

    private fun editTaskCallback(request: EditTaskRequest): Callback<EditTaskResponse> = object : Callback<EditTaskResponse> {
        override fun onFailure(call: Call<EditTaskResponse>, t: Throwable) {
            view.editTaskFailed()
        }

        override fun onResponse(call: Call<EditTaskResponse>, response: Response<EditTaskResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK ->{
                        repository.updateTask(
                            request.id,
                            request.title,
                            request.content,
                            request.taskPriority
                        )
                        view.editTaskSuccessful()
                    }
                }
            } else
                view.editTaskError(codeToMessage(response.code()))
        }

    }
}