package hr.ferit.zvonimirpavlovic.taskie.di

import hr.ferit.zvonimirpavlovic.taskie.presentation.*
import hr.ferit.zvonimirpavlovic.taskie.ui.details.fragment.TaskDetailsFragmentContract
import hr.ferit.zvonimirpavlovic.taskie.ui.details.fragment.UpdateTaskFragmentContract
import hr.ferit.zvonimirpavlovic.taskie.ui.login.LoginActivityContract
import hr.ferit.zvonimirpavlovic.taskie.ui.register.RegisterActivityContract
import hr.ferit.zvonimirpavlovic.taskie.ui.splash.SplashActivityContract
import hr.ferit.zvonimirpavlovic.taskie.ui.task_list.fragment.AddTaskFragmentContract
import hr.ferit.zvonimirpavlovic.taskie.ui.task_list.fragment.TasksFragmentContract
import org.koin.dsl.module

val presentationModule = module {
    factory<AddTaskFragmentContract.Presenter> { AddTaskFragmentPresenter(get(),get()) }
    factory<LoginActivityContract.Presenter> { LoginActivityPresenter(get(),get()) }
    factory<RegisterActivityContract.Presenter> {RegisterActivityPresenter(get())}
    factory<SplashActivityContract.Presenter> { SplashActivityPresenter(get()) }
    factory<TaskDetailsFragmentContract.Presenter> { TaskDetailsFragmentPresenter(get(),get()) }
    factory<TasksFragmentContract.Presenter> { TasksFragmentPresenter(get(),get()) }
    factory<UpdateTaskFragmentContract.Presenter> { UpdateTaskFragmentPresenter(get(),get()) }
}