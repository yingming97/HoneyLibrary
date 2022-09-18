package pham.hien.honeylibrary.Animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import pham.hien.honeylibrary.View.Animation.BaseCreative

class AlphaAnimation {
    fun visibleAnimation(target: View, time: Long) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.ALPHA,
                0f,
                1f
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                target.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        baseCreative.startAnimationTogether()
    }

    fun goneAnimation(target: View, time: Long) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.ALPHA,
                1f,
                0f
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                target.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        baseCreative.startAnimationTogether()
    }

    fun goneAnimation(target: View, time: Long, listener: Animator.AnimatorListener?) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.ALPHA,
                1f,
                0f
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(listener)
        baseCreative.startAnimationTogether()
    }


    fun visibleAnimation(target: View, time: Long, listener: Animator.AnimatorListener?) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.ALPHA,
                0f,
                1f
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(listener)
        baseCreative.startAnimationTogether()
    }
}