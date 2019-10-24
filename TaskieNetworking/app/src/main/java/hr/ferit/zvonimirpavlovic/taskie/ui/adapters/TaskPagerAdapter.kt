package hr.ferit.zvonimirpavlovic.taskie.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.ferit.zvonimirpavlovic.taskie.common.*
import hr.ferit.zvonimirpavlovic.taskie.ui.about.AboutAppFragment
import hr.ferit.zvonimirpavlovic.taskie.ui.about.AboutAuthorFragment

class TaskPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            POSITION_ZERO -> AboutAppFragment.newInstance()
            POSITION_ONE -> AboutAuthorFragment.newInstance()
            else -> AboutAppFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            POSITION_ZERO -> ABOUT_APP
            POSITION_ONE -> ABOUT_AUTHOR
            else -> DEFAULT
        }
    }

    override fun getCount() = PAGES_NUM

}