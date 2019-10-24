package hr.ferit.zvonimirpavlovic.taskie.ui.task_list

import android.view.MenuItem
import androidx.fragment.app.Fragment
import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.about.AboutFragment
import hr.ferit.zvonimirpavlovic.taskie.ui.task_list.fragment.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        showFragment(TasksFragment.newInstance())
        bottomNavigation.setOnNavigationItemSelectedListener { selectFragment(it) }
    }

    private fun selectFragment(it: MenuItem): Boolean {
        var selectedFragment: Fragment? = null
        when (it.itemId) {
            R.id.navTasks -> selectedFragment =
                TasksFragment.newInstance()
            R.id.navAbout -> selectedFragment = AboutFragment.newInstance()
        }
        if (selectedFragment != null) {
            showFragment(selectedFragment)
        }
        return true
    }

}