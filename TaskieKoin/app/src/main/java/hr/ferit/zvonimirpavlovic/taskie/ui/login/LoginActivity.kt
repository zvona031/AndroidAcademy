package hr.ferit.zvonimirpavlovic.taskie.ui.login

import android.content.Intent
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.displayToast
import hr.ferit.zvonimirpavlovic.taskie.common.onClick
import hr.ferit.zvonimirpavlovic.taskie.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.taskie.prefs.provideSharedPrefs
import hr.ferit.zvonimirpavlovic.taskie.presentation.LoginActivityPresenter
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.register.RegisterActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.task_list.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity(), LoginActivityContract.View {

    private val presenter by inject<LoginActivityContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.activity_login

    override fun setUpUi() {
        login.onClick { loginClicked() }
        goToRegister.onClick { goToRegisterClicked() }
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    override fun goToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginSuccessful() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginFailed() {
        displayToast(getString(R.string.no_internet))
    }

    override fun onLoginError(message: String) {
        displayToast(message)
    }

    private fun goToRegisterClicked() {
        presenter.onGoToRegisterClicked()
    }

    private fun loginClicked() {
        presenter.onLoginClicked(email.text.toString(), password.text.toString())
    }
}
