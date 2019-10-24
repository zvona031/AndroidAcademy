package hr.ferit.zvonimirpavlovic.taskie.presentation

import hr.ferit.zvonimirpavlovic.taskie.prefs.SharedPrefsHelper
import hr.ferit.zvonimirpavlovic.taskie.ui.splash.SplashActivityContract

class SplashActivityPresenter(private val preferences: SharedPrefsHelper) : SplashActivityContract.Presenter {

    private lateinit var view: SplashActivityContract.View

    override fun setView(view: SplashActivityContract.View) {
        this.view = view
    }

    override fun onCheckPrefs() {
       if(preferences.getUserToken().isEmpty()) view.onTokenEmpty()
        else view.onTokenNotEmpty()
    }
}