package hr.ferit.zvonimirpavlovic.taskie.presentation

import hr.ferit.zvonimirpavlovic.taskie.common.RESPONSE_OK
import hr.ferit.zvonimirpavlovic.taskie.common.codeToMessage
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.networking.interactor.TaskieInteractor
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRepository
import hr.ferit.zvonimirpavlovic.taskie.ui.details.fragment.TaskDetailsFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskDetailsFragmentPresenter(
    private val interactor: TaskieInteractor,
    private val repository: TaskRepository
) : TaskDetailsFragmentContract.Presenter {

    private lateinit var view: TaskDetailsFragmentContract.View

    override fun setView(view: TaskDetailsFragmentContract.View) {
        this.view = view
    }

    override fun onTryDisplayTask(taskID: String) {
        interactor.getTaskById(taskID,getTaskByIdCallback())
    }

    private fun getTaskByIdCallback(): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {
            view.tryDisplayTaskFailed()
        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()?.let { view.tryDisplayTaskSuccessful(it) }
                }
            } else view.tryDisplayTaskError(codeToMessage(response.code()))
        }
    }

    override fun getTaskById(id: String): BackendTask? {
        return repository.getTaskById(id)
    }
}