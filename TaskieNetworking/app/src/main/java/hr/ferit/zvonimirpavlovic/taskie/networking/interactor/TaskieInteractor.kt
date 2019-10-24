package hr.ferit.zvonimirpavlovic.taskie.networking.interactor

import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.AddTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.request.EditTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.request.UserDataRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.*
import retrofit2.Callback

interface TaskieInteractor {

    fun getTasks(taskieResponseCallback: Callback<GetTasksResponse>)

    fun register(request: UserDataRequest, registerCallback: Callback<RegisterResponse>)

    fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>)

    fun save(request: AddTaskRequest, saveCallback: Callback<BackendTask>)

    fun delete(id: String, deleteCallback: Callback<DeleteResponse>)

    fun edit (request: EditTaskRequest, editTaskCallback: Callback<EditTaskResponse>)

    fun getTaskById(id: String, getTaskByIdCallback:Callback<BackendTask>)
}