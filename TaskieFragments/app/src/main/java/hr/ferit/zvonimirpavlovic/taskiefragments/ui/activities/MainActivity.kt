package hr.ferit.zvonimirpavlovic.taskiefragments.ui.activities


import android.view.MenuItem
import androidx.fragment.app.Fragment
import hr.ferit.zvonimirpavlovic.taskiefragments.R
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.activities.base.BaseActivity
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments.AboutFragment
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments.TasksFragment
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
            R.id.navTasks -> selectedFragment = TasksFragment()
            R.id.navAbout -> selectedFragment = AboutFragment()
        }
        if (selectedFragment != null) {
            showFragment(selectedFragment)
        }
        return true
    }

}