package pham.hien.honeylibrary.Animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.view.View
import android.view.animation.Interpolator

class BaseObjectAnimator {
    private var view: View? = null
    private var objectAnimator: ObjectAnimator? = null
    private val objectAnimators = ArrayList<ObjectAnimator?>()

object BaseObjectAnimator{
    var TRANSLATION_Y = "translationY"
    var TRANSLATION_X = "translationX"
    var ALPHA = "alpha"
    var ROTATION = "rotation"
    var ROTATION_Y = "rotationY"
    var ROTATION_X = "rotationX"
    var SCALE_X = "scaleX"
    var SCALE_Y = "scaleY"
    var X = "x"
    var Y = "y"
    var PIVOT_X = "pivotX"
    var PIVOT_Y = "pivotY"
    var BACKGROUND_COLOR = "backgroundColor"
    var CLIP_BOUNDS = "clipBounds"
}


    constructor(view: View?) {
        objectAnimator = ObjectAnimator()
        this.view = view
    }

    constructor() {
        objectAnimator = ObjectAnimator()
    }

    fun setAnimator(view: View?, type: String?, vararg listPoint: Float) {
        objectAnimator = ObjectAnimator.ofFloat(view, type, *listPoint)
    }

    fun setAnimator(type: String?, vararg listPoint: Float) {
        objectAnimator = ObjectAnimator.ofFloat(view, type, *listPoint)
    }

    fun setItemAnimator(view: View?, type: String?, vararg listPoint: Float) {
        objectAnimator = ObjectAnimator.ofFloat(view, type, *listPoint)
        objectAnimators.add(objectAnimator)
    }

    fun setItemAnimator(type: String?, vararg listPoint: Float) {
        objectAnimator = ObjectAnimator.ofFloat(view, type, *listPoint)
        objectAnimators.add(objectAnimator)
    }

    fun getObjectAnimator(): ObjectAnimator? {
        return objectAnimator
    }

    fun getListObjectAnimator(): ArrayList<ObjectAnimator?>? {
        return objectAnimators
    }


    fun setInterpolator(interpolator: Interpolator?) {
        objectAnimator!!.interpolator = interpolator
    }

    fun makeAnimator(view: View?, type: String?, vararg listPoint: Float): ObjectAnimator? {
        return ObjectAnimator.ofFloat(view, type, *listPoint)
    }

    fun makeAnimator(type: String?, vararg listPoint: Float): ObjectAnimator? {
        return ObjectAnimator.ofFloat(view, type, *listPoint)
    }

    fun setDuration(time: Long) {
        objectAnimator!!.duration = time
    }

    fun setRepeatCount(times: Int) {
        objectAnimator!!.repeatCount = times
    }

    fun setRepeatMode(value: Int) {
        objectAnimator!!.repeatMode = value
    }

    fun addListener(animatorListener: Animator.AnimatorListener?) {
        objectAnimator!!.addListener(animatorListener)
    }

    fun cancelAnim() {
        objectAnimator!!.cancel()
    }

    fun makeAnimatorEvaluator(
        view: View?,
        type: String?,
        typeEvaluator: TypeEvaluator<*>?
    ): ObjectAnimator? {
        return ObjectAnimator.ofObject(view, type, typeEvaluator)
    }

    fun makeAnimatorValueHolder(
        view: View?,
        vararg values: PropertyValuesHolder?
    ): ObjectAnimator? {
        return ObjectAnimator.ofPropertyValuesHolder(view, *values)
    }
}