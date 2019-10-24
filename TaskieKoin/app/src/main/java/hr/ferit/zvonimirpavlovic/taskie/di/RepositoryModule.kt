package hr.ferit.zvonimirpavlovic.taskie.di

import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRepository
import hr.ferit.zvonimirpavlovic.taskie.persistence.TaskRoomRepository
import hr.ferit.zvonimirpavlovic.taskie.prefs.SharedPrefsHelper
import hr.ferit.zvonimirpavlovic.taskie.prefs.provideSharedPrefs
import org.koin.dsl.module

val repositoryModule = module {
    factory<TaskRepository> { TaskRoomRepository() }
    factory<SharedPrefsHelper> { provideSharedPrefs() }
}