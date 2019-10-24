package hr.ferit.zvonimirpavlovic.taskie.ui.task_list.fragment

import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask

interface TasksFragmentContract {

    interface View {

        fun getAllTasksSuccessful(tasks: MutableList<BackendTask>)

        fun getAllTasksFailed(repoTasks: MutableList<BackendTask>)

        fun getAllTasksError(message: String)

        fun saveOfflineTaskSuccessful(oldID: String, task: BackendTask)

        fun saveOfflineTaskFailed()

        fun saveOfflineTaskError(message: String)

        fun deleteTaskSuccessful(id: String)

        fun deleteTaskFailed()

        fun deleteTaskError(message: String)

    }

    interface Presenter {

        fun setView(view: TasksFragmentContract.View)

        fun onSaveOfflineTasks()

        fun onGetAllTasks()

        fun onDeleteTask(id: String)

    }

}