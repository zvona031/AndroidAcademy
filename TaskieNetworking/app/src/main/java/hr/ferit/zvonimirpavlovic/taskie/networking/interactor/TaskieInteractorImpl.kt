package hr.ferit.zvonimirpavlovic.taskie.networking.interactor

import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.AddTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.request.EditTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.request.UserDataRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.*
import hr.ferit.zvonimirpavlovic.taskie.networking.TaskieApiService
import retrofit2.Callback

class TaskieInteractorImpl(private val apiService: TaskieApiService) : TaskieInteractor {
    override fun getTaskById(id: String, getTaskByIdCallback: Callback<BackendTask>) {
        apiService.getTaskById(id).enqueue(getTaskByIdCallback)
    }

    override fun delete(id: String, deleteCallback: Callback<DeleteResponse>) {
        apiService.deleteTask(id).enqueue(deleteCallback)
    }

    override fun getTasks(taskieResponseCallback: Callback<GetTasksResponse>) {
        apiService.getTasks().enqueue(taskieResponseCallback)
    }

    override fun register(request: UserDataRequest, registerCallback: Callback<RegisterResponse>) {
        apiService.register(request).enqueue(registerCallback)
    }

    override fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>) {
        apiService.login(request).enqueue(loginCallback)
    }

    override fun save(request: AddTaskRequest, saveCallback: Callback<BackendTask>) {
        apiService.save(request).enqueue(saveCallback)
    }

    override fun edit(request: EditTaskRequest, editTaskCallback: Callback<EditTaskResponse>) {
        apiService.editTask(request).enqueue(editTaskCallback)
    }
}