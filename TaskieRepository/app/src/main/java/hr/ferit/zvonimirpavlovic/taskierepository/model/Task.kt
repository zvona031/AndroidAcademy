package hr.ferit.zvonimirpavlovic.taskierepository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskDbId: Long? = null,
    var id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)