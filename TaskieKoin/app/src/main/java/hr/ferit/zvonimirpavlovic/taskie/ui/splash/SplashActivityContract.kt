package hr.ferit.zvonimirpavlovic.taskie.ui.splash

interface SplashActivityContract {

    interface View {

        fun onTokenEmpty()

        fun onTokenNotEmpty()

    }

    interface Presenter {

        fun setView(view: SplashActivityContract.View)

        fun onCheckPrefs()
    }
}