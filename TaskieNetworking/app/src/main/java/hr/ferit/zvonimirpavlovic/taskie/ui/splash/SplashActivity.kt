package hr.ferit.zvonimirpavlovic.taskie.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.ferit.zvonimirpavlovic.taskie.prefs.provideSharedPrefs
import hr.ferit.zvonimirpavlovic.taskie.ui.register.RegisterActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.task_list.MainActivity

class SplashActivity : AppCompatActivity() {

    private val prefs = provideSharedPrefs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPrefs()
    }

    private fun checkPrefs() {
        if (prefs.getUserToken().isEmpty()) startSignIn() else startApp()
    }

    private fun startApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun startSignIn() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }
}