package hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments

interface TaskChangedListener {
    fun onTitleChanged()
    fun onPriorityChanged()
    fun onDescriptionChanged()
}