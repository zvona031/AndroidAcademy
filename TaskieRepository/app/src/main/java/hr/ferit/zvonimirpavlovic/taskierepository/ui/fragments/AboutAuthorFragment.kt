package hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments

import androidx.fragment.app.Fragment
import hr.ferit.zvonimirpavlovic.taskierepository.R
import hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments.base.BaseFragment

class AboutAuthorFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int =  R.layout.fragment_about_author

    companion object {
        fun newInstance(): Fragment {
            return AboutAuthorFragment()
        }
    }
}
