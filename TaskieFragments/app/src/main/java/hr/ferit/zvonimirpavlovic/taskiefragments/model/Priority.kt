package hr.ferit.zvonimirpavlovic.taskiefragments.model

import androidx.annotation.ColorRes
import hr.ferit.zvonimirpavlovic.taskiefragments.R

enum class Priority(@ColorRes private val colorRes: Int) {
    LOW(R.color.colorLow),
    MEDIUM(R.color.colorMedium),
    HIGH(R.color.colorHigh);

    fun getColor(): Int = colorRes
}
