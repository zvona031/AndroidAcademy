package hr.ferit.zvonimirpavlovic.taskierepository.db

import androidx.room.TypeConverter
import hr.ferit.zvonimirpavlovic.taskierepository.model.Priority

class Converter {
    companion object{
        @TypeConverter
        @JvmStatic
        fun fromPriority(value: Priority): Int {
            return value.getValue()
        }

        @TypeConverter
        @JvmStatic
        fun toPriority(value: Int): Priority {
            return when(value){
                Priority.LOW.getValue() -> Priority.LOW
                Priority.MEDIUM.getValue() -> Priority.MEDIUM
                Priority.HIGH.getValue() -> Priority.HIGH
                else -> Priority.LOW
            }
        }
    }
}