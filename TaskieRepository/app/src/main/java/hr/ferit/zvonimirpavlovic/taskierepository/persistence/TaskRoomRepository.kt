package hr.ferit.zvonimirpavlovic.taskierepository.persistence

import hr.ferit.zvonimirpavlovic.taskierepository.Taskie
import hr.ferit.zvonimirpavlovic.taskierepository.db.DaoProvider
import hr.ferit.zvonimirpavlovic.taskierepository.model.Priority
import hr.ferit.zvonimirpavlovic.taskierepository.model.Task

class TaskRoomRepository: TaskRepository{
    private var db = DaoProvider.getInstance(Taskie.getAppContext())

    private var taskDao = db.taskDao()

    override fun addTask(task: Task) {
         taskDao.insertTask(task)
    }

    override fun getTasks(): List<Task> {
        return taskDao.getTasks()
    }

    override fun getTaskById(id: Long): Task {
        return taskDao.getTaskById(id)
    }

    override fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override fun clearAllTasks() {
         taskDao.deleteAllTasks()
    }

    override fun changeTaskPriority(task: Task, newPriority: Priority) {
        taskDao.updateTaskPriority(task.taskDbId!!,newPriority)
    }

    override fun changeTaskTitle(task: Task, newTitle: String) {
        taskDao.updateTaskTitle(task.taskDbId!!,newTitle)
    }

    override fun changeTaskDescription(task: Task, newDescription: String) {
        taskDao.updateTaskDescription(task.taskDbId!!,newDescription)
    }
}

interface TaskRepository{
    fun addTask(task: Task)
    fun getTasks(): List<Task>
    fun getTaskById(id: Long): Task
    fun deleteTask(task: Task)
    fun clearAllTasks()
    fun changeTaskPriority(task: Task, newPriority: Priority)
    fun changeTaskTitle(task: Task, newTitle: String)
    fun changeTaskDescription(task: Task, newDescription: String)
}