package hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import hr.ferit.zvonimirpavlovic.taskierepository.R
import hr.ferit.zvonimirpavlovic.taskierepository.ui.adapters.TaskPagerAdapter
import hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : BaseFragment() {

    override fun getLayoutResourceId(): Int =  R.layout.fragment_about

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
    }

    private fun setupPager() {
        viewPager.adapter = TaskPagerAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    companion object{
        fun newInstance(): Fragment {
            return AboutFragment()
        }
    }
}

