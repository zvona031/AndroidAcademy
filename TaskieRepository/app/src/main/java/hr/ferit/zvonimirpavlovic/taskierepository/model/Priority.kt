package hr.ferit.zvonimirpavlovic.taskierepository.model

import androidx.annotation.ColorRes
import hr.ferit.zvonimirpavlovic.taskierepository.R

enum class Priority(@ColorRes private val colorRes: Int, private val value: Int) {
    LOW(R.color.colorLow,0),
    MEDIUM(R.color.colorMedium,1),
    HIGH(R.color.colorHigh,2);

    fun getColor(): Int = colorRes

    fun getValue(): Int = value
}
