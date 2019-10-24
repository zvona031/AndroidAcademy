package hr.ferit.zvonimirpavlovic.taskie.presentation

import hr.ferit.zvonimirpavlovic.taskie.common.RESPONSE_OK
import hr.ferit.zvonimirpavlovic.taskie.common.codeToMessage
import hr.ferit.zvonimirpavlovic.taskie.common.toRoomTask
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.AddTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.networking.interactor.TaskieInteractor
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRepository
import hr.ferit.zvonimirpavlovic.taskie.ui.task_list.fragment.AddTaskFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskFragmentPresenter(
    private val interactor: TaskieInteractor,
    private val repository: TaskRepository
) : AddTaskFragmentContract.Presenter {

    private lateinit var view: AddTaskFragmentContract.View

    override fun setView(view: AddTaskFragmentContract.View) {
        this.view = view
    }

    override fun saveTask(taskRequest: AddTaskRequest) {
        interactor.save(taskRequest, addTaskCallback(taskRequest.title, taskRequest.content, taskRequest.taskPriority))

    }

    private fun addTaskCallback(title: String, description: String, priority: Int): Callback<BackendTask> = object :
        Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>?, t: Throwable?) {
            var id = "0"
            if (repository.getTasks().isNotEmpty()) {
                id = repository.getTasks().last().id + 1
            }
            val task = BackendTask(
                id = id,
                title = title,
                userId = "",
                content = description,
                taskPriority = priority,
                isFavorite = false,
                isCompleted = false,
                sent = false
            )
            repository.addTask(task)
            view.saveTaskFailed(task)
        }

        override fun onResponse(call: Call<BackendTask>?, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()?.let {
                        repository.addTask(toRoomTask(it))
                        view.saveTaskSuccesful(it)
                    }
                }
            } else view.saveTaskError(codeToMessage(response.code()))
        }
    }

}