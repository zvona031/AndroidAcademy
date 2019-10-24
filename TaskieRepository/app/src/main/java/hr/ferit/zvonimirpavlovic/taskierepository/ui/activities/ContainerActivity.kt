package hr.ferit.zvonimirpavlovic.taskierepository.ui.activities

import hr.ferit.zvonimirpavlovic.taskierepository.R
import hr.ferit.zvonimirpavlovic.taskierepository.common.EXTRA_SCREEN_TYPE
import hr.ferit.zvonimirpavlovic.taskierepository.common.EXTRA_TASK_ID
import hr.ferit.zvonimirpavlovic.taskierepository.common.NO_TASK
import hr.ferit.zvonimirpavlovic.taskierepository.common.SCREEN_TASK_DETAILS
import hr.ferit.zvonimirpavlovic.taskierepository.ui.activities.base.BaseActivity
import hr.ferit.zvonimirpavlovic.taskierepository.ui.fragments.TaskDetailsFragment

class ContainerActivity: BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_container

    override fun setUpUi() {
        val screenType = intent.getStringExtra(EXTRA_SCREEN_TYPE)
        val id = intent.getLongExtra(EXTRA_TASK_ID, NO_TASK)
        if (screenType.isNotEmpty()) {
            when (screenType) {
                SCREEN_TASK_DETAILS -> showFragment(TaskDetailsFragment.newInstance(id))
            }
        } else {
            finish()
        }
    }
}

