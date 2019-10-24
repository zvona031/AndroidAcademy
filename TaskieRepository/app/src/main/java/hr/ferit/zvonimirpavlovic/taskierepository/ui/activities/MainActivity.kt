package hr.ferit.zvonimirpavlovic.taskierepository.ui.activities

import android.view.MenuItem
import androidx.fragment.app.Fragment
import hr.ferit.zvonimirpavlovic.taskierepository.R
import hr.ferit.zvonimirpavlovic.taskierepository.ui.activities.base.BaseActivity
import hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments.AboutFragment
import hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        showFragment(TasksFragment.newInstance())
        bottomNavigation.setOnNavigationItemSelectedListener { selectFragment(it) }
    }

    private fun selectFragment(it: MenuItem): Boolean {
        var selectedFragment: Fragment? = null
        when(it.itemId){
            R.id.navTasks -> selectedFragment = TasksFragment.newInstance()
            R.id.navAbout -> selectedFragment = AboutFragment.newInstance()
        }
        if (selectedFragment != null) {
            showFragment(selectedFragment)
        }
        return true
    }

}