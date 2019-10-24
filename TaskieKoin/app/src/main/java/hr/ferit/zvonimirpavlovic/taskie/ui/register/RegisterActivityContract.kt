package hr.ferit.zvonimirpavlovic.taskie.ui.register;

interface RegisterActivityContract {

    interface View {

        fun goToLogin()

        fun registerSuccessful()

        fun registerFailed()

        fun registerError(message: String)

    }

    interface Presenter {

        fun setView(view: RegisterActivityContract.View)

        fun onRegisterClicked(email: String, password: String, name: String)

        fun onGoToLoginClicked()

    }

}