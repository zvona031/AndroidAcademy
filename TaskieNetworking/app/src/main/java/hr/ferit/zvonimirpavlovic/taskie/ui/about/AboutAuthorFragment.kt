package hr.ferit.zvonimirpavlovic.taskie.ui.about

import androidx.fragment.app.Fragment
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseFragment

class AboutAuthorFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_about_author

    companion object {
        fun newInstance(): Fragment {
            return AboutAuthorFragment()
        }
    }
}
