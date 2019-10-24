package hr.ferit.zvonimirpavlovic.taskie.ui.details

import hr.ferit.zvonimirpavlovic.taskie.R
import hr.ferit.zvonimirpavlovic.taskie.common.EXTRA_SCREEN_TYPE
import hr.ferit.zvonimirpavlovic.taskie.common.EXTRA_TASK_ID
import hr.ferit.zvonimirpavlovic.taskie.common.SCREEN_TASK_DETAILS
import hr.ferit.zvonimirpavlovic.taskie.ui.base.BaseActivity
import hr.ferit.zvonimirpavlovic.taskie.ui.details.fragment.TaskDetailsFragment

class ContainerActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_container

    override fun setUpUi() {
        val screenType = intent.getStringExtra(EXTRA_SCREEN_TYPE)
        val id = intent.getStringExtra(EXTRA_TASK_ID)
        if (screenType.isNotEmpty()) {
            when (screenType) {
                SCREEN_TASK_DETAILS -> showFragment(TaskDetailsFragment.newInstance(id))
            }
        } else {
            finish()
        }
    }
}

