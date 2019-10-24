package hr.ferit.zvonimirpavlovic.taskierepository.db

import android.content.Context
import androidx.room.*
import hr.ferit.zvonimirpavlovic.taskierepository.model.Task

@Database(entities = [Task::class],version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class DaoProvider : RoomDatabase(){

    abstract fun taskDao():TaskDao

    companion object{
        private var instance: DaoProvider? = null

        fun getInstance(context: Context):DaoProvider {
            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DaoProvider::class.java,
                    "TaskDb")
                    .allowMainThreadQueries().build()
            }
            return instance as DaoProvider
        }
    }
}