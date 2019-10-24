package hr.ferit.zvonimirpavlovic.taskie.ui.login;

interface LoginActivityContract {

    interface View {

        fun goToRegister()

        fun onLoginSuccessful()

        fun onLoginFailed()

        fun onLoginError(message: String)

    }

    interface Presenter {

        fun setView(view: LoginActivityContract.View)

        fun onLoginClicked(email: String, password: String)

        fun onGoToRegisterClicked()
    }

}