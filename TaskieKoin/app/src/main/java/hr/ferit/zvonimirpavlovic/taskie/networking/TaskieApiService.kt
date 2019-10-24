package hr.ferit.zvonimirpavlovic.taskie.networking

import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask
import hr.ferit.zvonimirpavlovic.taskie.model.request.AddTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.request.EditTaskRequest
import hr.ferit.zvonimirpavlovic.taskie.model.request.UserDataRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.*
import retrofit2.Call
import retrofit2.http.*

interface TaskieApiService {

    @POST("/api/register")
    fun register(@Body userData: UserDataRequest): Call<RegisterResponse>

    @POST("/api/login")
    fun login(@Body userData: UserDataRequest): Call<LoginResponse>

    @GET("/api/note")
    fun getTasks(): Call<GetTasksResponse>

    @POST("/api/note")
    fun save(@Body taskData: AddTaskRequest): Call<BackendTask>

    @POST("/api/note/delete")
    fun deleteTask(@Query("id") id:String ): Call<DeleteResponse>

    @POST("/api/note/edit")
    fun editTask(@Body taskData: EditTaskRequest): Call<EditTaskResponse>

    @GET("/api/note/{id}")
    fun getTaskById(@Path("id") id: String): Call<BackendTask>
}