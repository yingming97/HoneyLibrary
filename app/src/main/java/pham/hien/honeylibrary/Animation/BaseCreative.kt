package pham.hien.honeylibrary.View.Animation
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.Interpolator

class BaseCreative {
    private var animatorSet = AnimatorSet()
    private var objs = ArrayList<Animator>()

    constructor() {
        animatorSet = AnimatorSet()
        objs = ArrayList()
    }

    fun startAnimationTogether() {
        animatorSet.playTogether(objs)
        animatorSet.start()
    }

    fun startAnimationSequentially() {
        animatorSet.playSequentially(objs)
        animatorSet.start()
    }

    fun endAnimaton() {
        animatorSet.end()
    }

    fun addListener(animatorListener: Animator.AnimatorListener?) {
        animatorSet.addListener(animatorListener)
    }

    fun setDuration(time: Long) {
        animatorSet.duration = time
    }

    fun setDelay(delay: Long) {
        animatorSet.startDelay = delay
    }

    fun getAnimatorSet(): AnimatorSet? {
        return animatorSet
    }

    fun getObjs(): ArrayList<Animator>? {
        return objs
    }

    fun addAnimator(animator: ObjectAnimator) {
        objs.add(animator)
    }

    fun setInterpolator(interporlator: Interpolator?) {
        animatorSet.interpolator = interporlator
    }

    fun addListAnimator(animators: ArrayList<ObjectAnimator>?) {
        objs.addAll(animators!!)
    }

    fun setRepeatModeAll(value: Int) {
        for (i in objs.indices) {
            val objectAnimator = objs[i] as ObjectAnimator
            objectAnimator.repeatMode = value
        }
    }

    fun setRepeatCountAll(value: Int) {
        for (i in objs.indices) {
            val objectAnimator = objs[i] as ObjectAnimator
            objectAnimator.repeatCount = value
        }
    }

    fun setRepeatModeChild(objectAnimator: ObjectAnimator, value: Int) {
        objectAnimator.repeatMode = value
    }

    fun setRepeatCountChild(objectAnimator: ObjectAnimator, value: Int) {
        objectAnimator.repeatCount = value
    }
}