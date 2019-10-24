package hr.ferit.zvonimirpavlovic.taskie.presentation

import hr.ferit.zvonimirpavlovic.taskie.common.RESPONSE_OK
import hr.ferit.zvonimirpavlovic.taskie.common.codeToMessage
import hr.ferit.zvonimirpavlovic.taskie.common.toRoomTask
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.AddTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.DeleteResponse
import hr.ferit.zvonimirpavlovic.taskie.model.response.GetTasksResponse
import hr.ferit.zvonimirpavlovic.taskie.networking.interactor.TaskieInteractor
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRepository
import hr.ferit.zvonimirpavlovic.taskie.ui.task_list.fragment.TasksFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksFragmentPresenter(private val interactor: TaskieInteractor,
                             private val repository: TaskRepository
) : TasksFragmentContract.Presenter {

    private lateinit var view: TasksFragmentContract.View

    override fun setView(view: TasksFragmentContract.View) {
        this.view = view
    }

    override fun onSaveOfflineTasks() {
        repository.getTasks().forEach {
            if (it.sent == false) {
                interactor.save(AddTaskRequest(it.title, it.content, it.taskPriority), saveOfflineTaskCallback(it.id))
            }
        }
    }

    override fun onGetAllTasks() {
        interactor.getTasks(getTasksCallback())
    }

    override fun onDeleteTask(id: String) {
        interactor.delete(id,getDeleteCallback(id))
    }

    private fun getTasksCallback(): Callback<GetTasksResponse> = object : Callback<GetTasksResponse> {
        override fun onFailure(call: Call<GetTasksResponse>?, t: Throwable?) {
            view.getAllTasksFailed(repository.getTasks() as MutableList<BackendTask>)
        }

        override fun onResponse(call: Call<GetTasksResponse>?, response: Response<GetTasksResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()!!.notes?.let {
                        for (backendTask in it) {
                            if (repository.getTaskById(backendTask.id) == null){
                                repository.addTask(toRoomTask(backendTask))
                            }
                        }
                        view.getAllTasksSuccessful(it) }
                }
            } else view.getAllTasksError(codeToMessage(response.code()))
        }
    }

    private fun getDeleteCallback(id: String): Callback<DeleteResponse> = object : Callback<DeleteResponse> {
        override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
            view.deleteTaskFailed()
        }

        override fun onResponse(call: Call<DeleteResponse>, response: Response<DeleteResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> {
                        val task = repository.getTaskById(id)
                        if (task != null) {
                            repository.deleteTask(task)
                        }
                        view.deleteTaskSuccessful(id)
                    }
                }
            } else view.deleteTaskError(codeToMessage(response.code()))
        }
    }

    private fun saveOfflineTaskCallback(oldId: String): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>?, t: Throwable?) {
            view.saveOfflineTaskFailed()

        }

        override fun onResponse(call: Call<BackendTask>?, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()?.let {
                        repository.getTaskById(oldId)?.let { it1 -> repository.deleteTask(it1) }
                        view.saveOfflineTaskSuccessful(oldId,it) }
                }
            } else view.saveOfflineTaskError(codeToMessage(response.code()))
        }
    }

}