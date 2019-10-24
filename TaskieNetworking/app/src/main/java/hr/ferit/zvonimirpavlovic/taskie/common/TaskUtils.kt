package hr.ferit.zvonimirpavlovic.taskie.common

import hr.ferit.zvonimirpavlovic.taskie.model.BackendPriorityTask
import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask

fun toPriority(int : Int):BackendPriorityTask {
    return when(int){
        1 -> BackendPriorityTask.LOW
        2 -> BackendPriorityTask.MEDIUM
        else -> BackendPriorityTask.HIGH
    }
}

fun codeToMessage(code: Int): String {
    return when(code){
        RESPONSE_BAD_REQUEST -> "Bad request!"
        RESPONSE_NOT_FOUND -> "Task not found!"
        UNAUTHORIZED_ACCESS -> "Unauthorized access!"
        SERVER_ERROR -> "Server error!"
        SERVICE_UNAVAILABLE -> "Service unavailable!"
        else -> "Unknown error!"
    }
}

fun toRoomTask(task: BackendTask): BackendTask {
    return BackendTask(task.id,task.userId,task.title,task.content,task.isFavorite,task.taskPriority,task.isCompleted)
}