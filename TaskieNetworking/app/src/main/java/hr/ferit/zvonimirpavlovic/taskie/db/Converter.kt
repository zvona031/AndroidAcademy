package hr.ferit.zvonimirpavlovic.taskie.db

import androidx.room.TypeConverter
import hr.ferit.zvonimirpavlovic.taskie.model.BackendPriorityTask

class Converter {
    companion object{
        @TypeConverter
        @JvmStatic
        fun fromPriority(value: BackendPriorityTask): Int {
            return value.getValue()
        }

        @TypeConverter
        @JvmStatic
        fun toPriority(value: Int): BackendPriorityTask {
            return when(value){
                BackendPriorityTask.LOW.getValue() -> BackendPriorityTask.LOW
                BackendPriorityTask.MEDIUM.getValue() -> BackendPriorityTask.MEDIUM
                else -> BackendPriorityTask.HIGH
            }
        }
    }
}