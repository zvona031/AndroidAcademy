package hr.ferit.zvonimirpavlovic.taskie.model.request

data class EditTaskRequest(val id: String,val title: String, val content: String, val taskPriority: Int)