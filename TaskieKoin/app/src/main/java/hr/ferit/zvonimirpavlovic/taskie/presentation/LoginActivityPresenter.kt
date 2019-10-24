package hr.ferit.zvonimirpavlovic.taskie.presentation

import hr.ferit.zvonimirpavlovic.taskie.common.RESPONSE_OK
import hr.ferit.zvonimirpavlovic.taskie.common.codeToMessage
import hr.ferit.zvonimirpavlovic.taskie.model.request.UserDataRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.LoginResponse
import hr.ferit.zvonimirpavlovic.taskie.networking.interactor.TaskieInteractor
import hr.ferit.zvonimirpavlovic.taskie.prefs.SharedPrefsHelper
import hr.ferit.zvonimirpavlovic.taskie.ui.login.LoginActivityContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityPresenter(
    private val interactor: TaskieInteractor,
    private val preferences: SharedPrefsHelper
) : LoginActivityContract.Presenter {

    private lateinit var view: LoginActivityContract.View

    override fun setView(view: LoginActivityContract.View) {
        this.view = view
    }

    override fun onLoginClicked(email: String, password: String) {
        interactor.login(UserDataRequest(email, password), loginCallback())
    }

    override fun onGoToRegisterClicked() {
        view.goToRegister()
    }

    private fun loginCallback(): Callback<LoginResponse> = object : Callback<LoginResponse> {
        override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
            view.onLoginFailed()
        }

        override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> {
                        response.body()?.token?.let { preferences.storeUserToken(it) }
                        view.onLoginSuccessful()
                    }
                }
            } else view.onLoginError(codeToMessage(response.code()))
        }
    }

}