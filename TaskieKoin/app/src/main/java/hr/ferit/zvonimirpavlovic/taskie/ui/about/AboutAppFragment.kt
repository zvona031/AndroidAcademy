package hr.ferit.zvonimirpavlovic.taskie.ui.about


import androidx.fragment.app.Fragment
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseFragment

class AboutAppFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_about_app

    companion object {
        fun newInstance(): Fragment {
            return AboutAppFragment()
        }
    }
}
