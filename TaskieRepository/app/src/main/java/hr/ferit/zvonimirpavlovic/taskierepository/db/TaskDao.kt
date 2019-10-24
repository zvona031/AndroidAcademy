package hr.ferit.zvonimirpavlovic.taskierepository.db

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import hr.ferit.zvonimirpavlovic.taskierepository.model.Priority
import hr.ferit.zvonimirpavlovic.taskierepository.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getTasks(): List<Task>

    @Query("SELECT * FROM Task WHERE taskDbId = :id")
    fun getTaskById(id: Long): Task

    @Insert(onConflict= IGNORE)
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE  from Task")
    fun deleteAllTasks()

    @Query("UPDATE Task SET priority= :taskPriority WHERE taskDbId= :taskId")
    fun updateTaskPriority(taskId: Long, taskPriority: Priority)

    @Query("UPDATE Task SET title= :taskTitle WHERE taskDbId= :taskId")
    fun updateTaskTitle(taskId: Long,taskTitle: String)

    @Query("UPDATE Task SET description= :taskDescription WHERE taskDbId= :taskId")
    fun updateTaskDescription(taskId: Long,taskDescription: String)
}