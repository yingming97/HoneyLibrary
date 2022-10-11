package pham.hien.honeylibrary.Utils

import android.content.res.AssetManager
import android.graphics.Typeface

open class TypefaceUtils {

    companion object {

        fun getSemiBold(assetManager: AssetManager): Typeface {
            return Typeface.createFromAsset(assetManager, "font/roboto_bold.xml")
        }

        fun getMedium(assetManager: AssetManager): Typeface {
            return Typeface.createFromAsset(assetManager, "font/roboto_medium.xml")
        }

        fun getBold(assetManager: AssetManager): Typeface {
            return Typeface.createFromAsset(assetManager, "font/roboto_bold.xml")
        }

        fun getRegular(assetManager: AssetManager): Typeface {
            return Typeface.createFromAsset(assetManager, "font/roboto.xml")
        }
    }
}