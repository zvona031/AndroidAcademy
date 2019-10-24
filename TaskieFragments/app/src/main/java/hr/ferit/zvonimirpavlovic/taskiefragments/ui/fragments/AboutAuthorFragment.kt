package hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments

import androidx.fragment.app.Fragment
import hr.ferit.zvonimirpavlovic.taskiefragments.R
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments.base.BaseFragment

class AboutAuthorFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int =  R.layout.fragment_about_author

    companion object {
        fun newInstance(): Fragment {
            return AboutAuthorFragment()
        }
        const val ABOUT_AUTHOR = "ABOUT AUTHOR"
    }
}
