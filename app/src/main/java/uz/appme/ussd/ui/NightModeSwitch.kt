package uz.appme.ussd.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.Switch
import androidx.core.content.ContextCompat
import uz.appme.ussd.R

class NightModeSwitch : Switch {
    internal var context: Context
    constructor(context: Context) : super(context) {
        this.context = context
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.context = context
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        changeSwitchAppearance(checked)
    }
    private fun changeSwitchAppearance(isChecked: Boolean) {
        if (isChecked) {
            try {
                thumbDrawable = ContextCompat.getDrawable(context, R.drawable.ic_carousel_thumb)
                trackDrawable = ContextCompat.getDrawable(context, R.drawable.ic_grid_carousel_track)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                thumbDrawable = ContextCompat.getDrawable(context, R.drawable.ic_grid_thumb)
                trackDrawable = ContextCompat.getDrawable(context, R.drawable.ic_grid_carousel_track)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}