package hr.ferit.zvonimirpavlovic.taskie.model.response

import hr.ferit.zvonimirpavlovic.taskie.model.BackendTask

class GetTasksResponse(val notes: MutableList<BackendTask>? = mutableListOf())