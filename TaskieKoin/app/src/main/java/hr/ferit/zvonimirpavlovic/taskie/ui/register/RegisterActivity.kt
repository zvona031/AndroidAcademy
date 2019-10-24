package hr.ferit.zvonimirpavlovic.taskie.ui.register

import android.content.Intent
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.displayToast
import hr.ferit.zvonimirpavlovic.taskie.common.onClick
import hr.ferit.zvonimirpavlovic.taskie.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.taskie.presentation.RegisterActivityPresenter
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject

class RegisterActivity : BaseActivity(), RegisterActivityContract.View {

    private val presenter by inject<RegisterActivityContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.activity_register

    override fun setUpUi() {
        register.onClick { registerClicked() }
        goToLogin.onClick { goToLoginClicked() }
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    override fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun registerSuccessful() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun registerFailed() {
        displayToast(getString(R.string.no_internet))
    }

    override fun registerError(message: String) {
        displayToast(message)
    }

    private fun goToLoginClicked() {
        presenter.onGoToLoginClicked()
    }

    private fun registerClicked() {
        presenter.onRegisterClicked(email.text.toString(), password.text.toString(), name.text.toString())
    }
}
