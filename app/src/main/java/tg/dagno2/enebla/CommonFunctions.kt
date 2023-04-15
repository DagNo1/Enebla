package tg.dagno2.enebla

import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible

class CommonFunctions {
    companion object{
        fun hideProgressBar(bar: View, show : View){
            bar.isVisible = false
            show.isVisible = true
        }
        fun showProgressBar(bar: View, hide: View){
            bar.isVisible = true
            hide.isVisible = false
        }
        fun str(input: EditText): String =  input.text.toString() // thought it would make it cleaner str == stringer
        fun disableFields(views: List<View>){
            for (i in views){
                i.isEnabled = false
            }
        }
        fun enableFields(views: List<View>){
            for (i in views){
                i.isEnabled = true
            }
        }
    }
}