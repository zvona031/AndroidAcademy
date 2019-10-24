package hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments


import androidx.fragment.app.Fragment
import hr.ferit.zvonimirpavlovic.taskiefragments.R
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments.base.BaseFragment

class AboutAppFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int =  R.layout.fragment_about_app

    companion object {
        fun newInstance(): Fragment {
            return AboutAppFragment()
        }
        const val ABOUT_APP = "ABOUT APP"
    }
}
