package hr.ferit.zvonimirpavlovic.taskie.ui.login

import android.content.Intent
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.*
import hr.ferit.zvonimirpavlovic.taskie.model.request.UserDataRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.LoginResponse
import hr.ferit.zvonimirpavlovic.taskie.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.taskie.prefs.provideSharedPrefs
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.register.RegisterActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.task_list.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {

    private val interactor = BackendFactory.getTaskieInteractor()

    private val prefs = provideSharedPrefs()

    override fun getLayoutResourceId(): Int = R.layout.activity_login

    override fun setUpUi() {
        login.onClick { loginClicked() }
        goToRegister.onClick { goToRegisterClicked() }
    }

    private fun goToRegisterClicked() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginClicked() {
        interactor.login(UserDataRequest((email.text.toString()), password.text.toString()), loginCallback())
    }

    private fun loginCallback(): Callback<LoginResponse> = object : Callback<LoginResponse> {
        override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
            handleOnFailure()
        }

        override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse(response.body())
                }
            } else handleElseResponses(response.code())
        }
    }

    private fun handleOkResponse(body: LoginResponse?) {
        body?.token?.let { prefs.storeUserToken(it) }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleElseResponses(code: Int) {
        displayToast(codeToMessage(code))
    }

    private fun handleOnFailure() {
        displayToast(getString(R.string.no_internet))
    }
}
