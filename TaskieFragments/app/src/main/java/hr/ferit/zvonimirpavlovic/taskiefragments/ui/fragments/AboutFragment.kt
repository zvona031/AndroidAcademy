package hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments

import android.os.Bundle
import android.view.View
import hr.ferit.zvonimirpavlovic.taskiefragments.R
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.adapters.TaskPagerAdapter
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments.base.BaseFragment
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
}

