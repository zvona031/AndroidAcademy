package hr.ferit.zvonimirpavlovic.taskie.persistence

import hr.ferit.zvonimirpavlovic.taskie.Taskie
import hr.ferit.zvonimirpavlovic.taskie.db.DaoProvider
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask

class TaskRoomRepository: TaskRepository{
    private var db = DaoProvider.getInstance(Taskie.getAppContext())

    private var taskDao = db.taskDao()

    override fun addTask(task: BackendTask) {
         taskDao.insertTask(task)
    }

    override fun getTasks(): List<BackendTask> {
        return taskDao.getTasks()
    }

    override fun getTaskById(id: String): BackendTask? {
        return taskDao.getTaskById(id)
    }

    override fun deleteTask(task: BackendTask) {
        taskDao.deleteTask(task)
    }

    override fun clearAllTasks() {
         taskDao.deleteAllTasks()
    }

    override fun updateTask(id: String,title: String,description: String, priorityTask: Int ){
        taskDao.updateTask(id,title,description,priorityTask)
    }

    override fun taskSent(id: String) {
        taskDao.taskSent(id,true)
    }
}

interface TaskRepository{
    fun addTask(task: BackendTask)
    fun getTasks(): List<BackendTask>
    fun getTaskById(id: String): BackendTask?
    fun deleteTask(task: BackendTask)
    fun clearAllTasks()
    fun updateTask(id: String,title: String,description: String, priorityTask: Int)
    fun taskSent(id: String)
}