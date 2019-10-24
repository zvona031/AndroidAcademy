package hr.ferit.zvonimirpavlovic.taskiefragments.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments.AboutAppFragment
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments.AboutAppFragment.Companion.ABOUT_APP
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments.AboutAuthorFragment
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments.AboutAuthorFragment.Companion.ABOUT_AUTHOR

class TaskPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> AboutAppFragment.newInstance()
            1 -> AboutAuthorFragment.newInstance()
            else -> AboutAppFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> ABOUT_APP
            1 -> ABOUT_AUTHOR
            else -> "Default"
        }
    }

    override fun getCount() = 2
}