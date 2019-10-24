package hr.ferit.zvonimirpavlovic.taskie.ui.register

import android.content.Intent
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.RESPONSE_OK
import hr.ferit.zvonimirpavlovic.taskie.common.codeToMessage
import hr.ferit.zvonimirpavlovic.taskie.common.displayToast
import hr.ferit.zvonimirpavlovic.taskie.common.onClick
import hr.ferit.zvonimirpavlovic.taskie.model.request.UserDataRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.RegisterResponse
import hr.ferit.zvonimirpavlovic.taskie.networking.BackendFactory
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : BaseActivity() {

    private val interactor = BackendFactory.getTaskieInteractor()

    override fun getLayoutResourceId(): Int = R.layout.activity_register

    override fun setUpUi() {
        register.onClick { registerClicked() }
        goToLogin.onClick { goToLoginClicked() }
    }

    private fun goToLoginClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerClicked() {
        interactor.register(
            UserDataRequest(email.text.toString(), password.text.toString(), name.text.toString()),
            registerCallback()
        )
    }

    private fun registerCallback(): Callback<RegisterResponse> = object : Callback<RegisterResponse> {
        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
            handleOnFailure()
        }

        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse()
                }
            } else handleElseResponses(response.code())
        }
    }

    private fun handleOkResponse() {
        val intent = Intent(this, LoginActivity::class.java)
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
