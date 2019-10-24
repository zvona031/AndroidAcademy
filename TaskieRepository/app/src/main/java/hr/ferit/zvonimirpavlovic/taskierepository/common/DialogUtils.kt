package hr.ferit.zvonimirpavlovic.taskierepository.common


import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity


fun showDialog(activity: FragmentActivity?,title: String, iconId: Int, positiveButton: Int, positiveListener:() -> Unit?, negativeButton: Int, negativeListener:() -> Unit?){
    activity?.let {
        AlertDialog.Builder(it).setTitle(title)
            .setIcon(iconId)
            .setPositiveButton(positiveButton){_,_ -> positiveListener()}
            .setNegativeButton(negativeButton){_,_-> negativeListener()}
            .show()
}}

fun showDialog(activity: FragmentActivity?,title: String, iconId: Int, positiveButton: Int, positiveListener:() -> Unit?, negativeButton: Int){
    activity?.let {
        AlertDialog.Builder(it).setTitle(title)
            .setIcon(iconId)
            .setPositiveButton(positiveButton){_,_ -> positiveListener()}
            .setNegativeButton(negativeButton,null)
            .show()
    }
}