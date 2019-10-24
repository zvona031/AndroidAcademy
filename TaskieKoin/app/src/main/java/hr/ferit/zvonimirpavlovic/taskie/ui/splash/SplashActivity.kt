package hr.ferit.zvonimirpavlovic.taskie.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.ferit.zvonimirpavlovic.taskie.prefs.provideSharedPrefs
import hr.ferit.zvonimirpavlovic.taskie.presentation.SplashActivityPresenter
import hr.ferit.zvonimirpavlovic.taskie.ui.register.RegisterActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.task_list.MainActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity(), SplashActivityContract.View {

    private val presenter by inject<SplashActivityContract.Presenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setView(this)
        checkPrefs()
    }

    private fun checkPrefs() {
        presenter.onCheckPrefs()
    }

    override fun onTokenEmpty() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    override fun onTokenNotEmpty() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}