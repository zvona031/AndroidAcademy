package hr.ferit.zvonimirpavlovic.taskie.di

import hr.ferit.zvonimirpavlovic.taskie.networking.interactor.TaskieInteractor
import hr.ferit.zvonimirpavlovic.taskie.networking.interactor.TaskieInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    factory<TaskieInteractor> { TaskieInteractorImpl(get()) }
}