package pham.hien.honeylibrary.Utils

import android.content.res.AssetManager
import android.graphics.Typeface

open class TypefaceUtils {

    companion object {

        fun getSemiBold(assetManager: AssetManager): Typeface {
            return Typeface.createFromAsset(assetManager, "fonts/Poppins-SemiBold.ttf")
        }

        fun getMedium(assetManager: AssetManager): Typeface {
            return Typeface.createFromAsset(assetManager, "fonts/Poppins-Medium.ttf")
        }

        fun getBold(assetManager: AssetManager): Typeface {
            return Typeface.createFromAsset(assetManager, "fonts/Poppins-Bold.ttf")
        }

        fun getRegular(assetManager: AssetManager): Typeface {
            return Typeface.createFromAsset(assetManager, "fonts/Poppins-Regular.ttf")
        }
    }
}