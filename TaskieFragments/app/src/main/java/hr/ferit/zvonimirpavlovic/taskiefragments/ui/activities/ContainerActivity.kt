package hr.ferit.zvonimirpavlovic.taskiefragments.ui.activities

import hr.ferit.zvonimirpavlovic.taskiefragments.R
import hr.ferit.zvonimirpavlovic.taskiefragments.common.EXTRA_SCREEN_TYPE
import hr.ferit.zvonimirpavlovic.taskiefragments.common.EXTRA_TASK_ID
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.activities.base.BaseActivity
import hr.ferit.zvonimirpavlovic.taskiefragments.ui.fragments.TaskDetailsFragment

class ContainerActivity: BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_container

    override fun setUpUi() {
        val screenType = intent.getStringExtra(EXTRA_SCREEN_TYPE)
        val id = intent.getIntExtra(EXTRA_TASK_ID, -1)
        if (screenType.isNotEmpty()) {
            when (screenType) {
                SCREEN_TASK_DETAILS -> showFragment(TaskDetailsFragment.newInstance(id))
            }
        } else {
            finish()
        }
    }

    companion object{
        const val SCREEN_TASK_DETAILS = "task_details"
    }
}

