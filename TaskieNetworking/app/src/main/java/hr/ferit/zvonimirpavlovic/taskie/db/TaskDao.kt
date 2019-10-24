package hr.ferit.zvonimirpavlovic.taskie.db

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask

@Dao
interface TaskDao {
    @Query("SELECT * FROM BackendTask")
    fun getTasks(): List<BackendTask>

    @Query("SELECT * FROM BackendTask WHERE id = :id")
    fun getTaskById(id: String): BackendTask

    @Insert(onConflict= IGNORE)
    fun insertTask(task: BackendTask)

    @Delete
    fun deleteTask(task: BackendTask)

    @Query("DELETE from BackendTask")
    fun deleteAllTasks()

    @Query("UPDATE BackendTask SET title= :title, content= :taskDescription, taskPriority= :taskPriority WHERE id= :taskId")
    fun updateTask(taskId: String,title: String,taskDescription: String,taskPriority: Int)

    @Query("UPDATE BackendTask SET sent= :isSent WHERE id= :id")
    fun taskSent(id: String,isSent: Boolean)

}