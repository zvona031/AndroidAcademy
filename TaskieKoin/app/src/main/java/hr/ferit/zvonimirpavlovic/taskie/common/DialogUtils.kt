package hr.ferit.zvonimirpavlovic.taskie.common


import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity


fun showYesNoDialog(activity: FragmentActivity?, title: String, iconId: Int, positiveListener:() -> Unit?, negativeListener:() -> Unit?){
    activity?.let {
        AlertDialog.Builder(it).setTitle(title)
            .setIcon(iconId)
            .setPositiveButton(android.R.string.yes){_,_ -> positiveListener()}
            .setNegativeButton(android.R.string.no){_,_-> negativeListener()}
            .show()
}}

fun showYesNoDialog(activity: FragmentActivity?, title: String, iconId: Int, positiveListener:() -> Unit?){
    activity?.let {
        AlertDialog.Builder(it).setTitle(title)
            .setIcon(iconId)
            .setPositiveButton(android.R.string.yes) { _, _ -> positiveListener()}
            .setNegativeButton(android.R.string.no){dialog,_ -> dialog.cancel()}
            .show()
    }
}