package hr.ferit.zvonimirpavlovic.taskie.presentation

import hr.ferit.zvonimirpavlovic.taskie.common.RESPONSE_OK
import hr.ferit.zvonimirpavlovic.taskie.common.codeToMessage
import hr.ferit.zvonimirpavlovic.taskie.model.request.UserDataRequest
import hr.ferit.zvonimirpavlovic.taskie.model.response.RegisterResponse
import hr.ferit.zvonimirpavlovic.taskie.networking.interactor.TaskieInteractor
import hr.ferit.zvonimirpavlovic.taskie.ui.register.RegisterActivityContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivityPresenter(
    private val interactor: TaskieInteractor
) : RegisterActivityContract.Presenter {

    private lateinit var view: RegisterActivityContract.View

    override fun setView(view: RegisterActivityContract.View) {
        this.view = view
    }

    override fun onRegisterClicked(email: String, password: String, name: String) {
        interactor.register(UserDataRequest(email, password, name), registerCallback())
    }

    private fun registerCallback(): Callback<RegisterResponse> = object : Callback<RegisterResponse> {
        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
            view.registerFailed()
        }

        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> view.registerSuccessful()
                }
            } else view.registerError(codeToMessage(response.code()))
        }
    }


    override fun onGoToLoginClicked() {
        view.goToLogin()
    }
}