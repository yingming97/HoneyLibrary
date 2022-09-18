package pham.hien.honeylibrary.Utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import pham.hien.honeylibrary.R

open class ScreenUtils {

    open fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        return display.height
    }

    open fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        return display.width
    }

    open fun getScreenSizeIncludingTopBottomBar(context: Context): IntArray {
        val screenDimensions = IntArray(2) // width[0], height[1]
        val x: Int
        val y: Int
        val orientation = context.resources.configuration.orientation
        val wm = context.getSystemService(AppCompatActivity.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val screenSize = Point()
        display.getRealSize(screenSize)
        x = screenSize.x
        y = screenSize.y
        screenDimensions[0] =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) x else y // width
        screenDimensions[1] =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) y else x // height
        return screenDimensions
    }


    open fun getHeightStatusBar(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            val heightStatusBar = context.resources.getDimensionPixelSize(resourceId)
            if (heightStatusBar > context.resources.getDimension(R.dimen._24sdp)) {
                context.resources.getDimension(R.dimen._24sdp).toInt()
            } else {
                heightStatusBar
            }
        } else {
            0
        }
    }

    open fun setMarginStatusBar(context: Context, view: View) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, getHeightStatusBar(context), 0, 0)
            view.requestLayout()
        }
    }

    open fun setMarginStatusBarLinearLayout(context: Context, view: View) {
        val topbarLp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        topbarLp.setMargins(0, getHeightStatusBar(context), 0, 0)
        view.layoutParams = topbarLp
    }

    open fun setMarginStatusBar(
        context: Context,
        view: View,
        w: Int,
        h: Int,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
    ) {
        val topbarLp = RelativeLayout.LayoutParams(w, h)
        topbarLp.setMargins(left, top + getHeightStatusBar(context), right, bottom)
        view.layoutParams = topbarLp
    }

    open fun setEnableView(enableView: View) {
        enableView.isEnabled = false
        Handler().postDelayed({ enableView.isEnabled = true }, 1000)
    }

    open fun setEnableView(enableView: View, time: Long) {
        enableView.isEnabled = false
        Handler().postDelayed({ enableView.isEnabled = true }, time)
    }

    open fun setPaddingStatusBar(context: Context?, view: View) {
        view.setPadding(0, ScreenUtils().getHeightStatusBar(context!!), 0, 0)
    }

    open fun getTextName(nameUser: String, numberText: Int): String? {
        var nameUser = nameUser
        nameUser = trimFirstCharacters(nameUser)
        if (nameUser.length > numberText) {
            val listName = nameUser.split(" ".toRegex()).toTypedArray()
            var temp = ""
            for (s in listName) {
                if (temp.length + s.length <= numberText) {
                    temp = "$temp $s"
                }
            }
            nameUser = if (temp == "") {
                nameUser.substring(0, numberText)
            } else {
                temp
            }
        }
        return nameUser
    }

    private fun trimFirstCharacters(s: String): String {
        return if (s.startsWith(" ")) {
            trimFirstCharacters(s.substring(1))
        } else {
            s
        }
    }
}