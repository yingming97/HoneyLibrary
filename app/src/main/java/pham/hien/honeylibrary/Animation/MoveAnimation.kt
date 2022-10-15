package pham.hien.honeylibrary.Animation
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import pham.hien.honeylibrary.View.Animation.BaseCreative

    fun openViewFromBottom(
        target: View,
        distance: Int,
        duration: Int,
        animatorListener: Animator.AnimatorListener?,
    ) {
        val baseCreative = BaseCreative()
        val objectAnimator = ObjectAnimator.ofFloat(
            target,
            BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_Y,
            distance.toFloat(),
            0f
        )
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        baseCreative.addAnimator(objectAnimator)
        // baseCreative.addAnimator(ObjectAnimator.ofFloat(target, BaseObjectAnimator.ALPHA, 0, 1));
        baseCreative.setDuration(duration.toLong())
        baseCreative.addListener(animatorListener)
        baseCreative.startAnimationTogether()
    }

    fun closeViewToBottom(
        target: View,
        distance: Int,
        duration: Int,
        listener: Animator.AnimatorListener?,
    ) {
        val baseCreative = BaseCreative()
        val objectAnimator = ObjectAnimator.ofFloat(
            target,
            BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_Y,
            0f,
            distance.toFloat()
        )
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        baseCreative.addAnimator(objectAnimator)
        baseCreative.setDuration(duration.toLong())
        baseCreative.addListener(listener)
        baseCreative.startAnimationTogether()
    }

    fun openViewFromBottom(target: View, distance: Int, duration: Int) {
        val baseCreative = BaseCreative()
        val objectAnimator = ObjectAnimator.ofFloat(
            target,
            BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_Y,
            distance.toFloat(),
            0f
        )
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        baseCreative.addAnimator(objectAnimator)
        baseCreative.setDuration(duration.toLong())
        baseCreative.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                target.visibility = View.VISIBLE
            }
        })
        baseCreative.startAnimationTogether()
    }


    fun closeViewToBottom(target: View, distance: Int, duration: Int) {
        val baseCreative = BaseCreative()
        val objectAnimator = ObjectAnimator.ofFloat(
            target,
            BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_Y,
            0f,
            distance.toFloat()
        )
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        baseCreative.addAnimator(objectAnimator)
        baseCreative.setDuration(duration.toLong())
        baseCreative.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                target.visibility = View.GONE
            }
        })
        baseCreative.startAnimationTogether()
    }

    fun openViewFromRight(
        target: View,
        distance: Int,
        time: Long,
        listener: Animator.AnimatorListener?,
    ) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_X,
                distance.toFloat(),
                0f
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(listener)
        baseCreative.startAnimationTogether()
    }

    fun closeViewToRight(
        target: View,
        distance: Int,
        time: Long,
        listener: Animator.AnimatorListener?,
    ) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_X,
                0f,
                distance.toFloat()
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(listener)
        baseCreative.startAnimationTogether()
    }

    fun openViewFromRight(target: View, distance: Int, time: Long) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_X,
                distance.toFloat(),
                0f
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                target.visibility = View.VISIBLE
            }
        })
        baseCreative.startAnimationTogether()
    }

    fun closeViewToRight(target: View, distance: Int, time: Long) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_X,
                0f,
                distance.toFloat()
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                target.visibility = View.GONE
            }
        })
        baseCreative.startAnimationTogether()
    }

    fun openViewFromLeft(
        target: View,
        distance: Int,
        time: Long,
        listener: Animator.AnimatorListener?,
    ) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_X,
                -distance.toFloat(),
                0f
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(listener)
        baseCreative.startAnimationTogether()
    }

    fun closeViewToLeft(
        target: View,
        distance: Int,
        time: Long,
        listener: Animator.AnimatorListener?,
    ) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_X,
                0f,
                -distance.toFloat()
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(listener)
        baseCreative.startAnimationTogether()
    }

    fun openViewFromLeft(target: View, distance: Int, time: Long) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_X,
                -distance.toFloat(),
                0f
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                target.visibility = View.VISIBLE
            }
        })
        baseCreative.startAnimationTogether()
    }

    fun closeViewToLeft(target: View, distance: Int, time: Long) {
        val baseCreative = BaseCreative()
        baseCreative.addAnimator(
            ObjectAnimator.ofFloat(
                target,
                BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_X,
                0f,
                -distance.toFloat()
            )
        )
        baseCreative.setDuration(time)
        baseCreative.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                target.visibility = View.GONE
            }
        })
        baseCreative.startAnimationTogether()
    }


    fun openViewFromTop(target: View, distance: Int, duration: Int) {
        val baseCreative = BaseCreative()
        val objectAnimator = ObjectAnimator.ofFloat(
            target,
            BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_Y,
            -distance.toFloat(),
            0f
        )
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        baseCreative.addAnimator(objectAnimator)
        baseCreative.setDuration(duration.toLong())
        baseCreative.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                target.visibility = View.VISIBLE
            }
        })
        baseCreative.startAnimationTogether()
    }

    fun closeViewFromTop(target: View, distance: Int, duration: Int) {
        val baseCreative = BaseCreative()
        val objectAnimator = ObjectAnimator.ofFloat(
            target,
            BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_Y,
            0f,
            -distance.toFloat()
        )
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        baseCreative.addAnimator(objectAnimator)
        baseCreative.setDuration(duration.toLong())
        baseCreative.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                target.visibility = View.GONE
            }
        })
        baseCreative.startAnimationTogether()
    }
    fun closeViewToTop(target: View, distance: Int, duration: Int) {
        val baseCreative = BaseCreative()
        val objectAnimator = ObjectAnimator.ofFloat(target,
            BaseObjectAnimator.BaseObjectAnimator.TRANSLATION_Y,
            0f,
            -distance.toFloat())
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        baseCreative.addAnimator(objectAnimator)
        baseCreative.setDuration(duration.toLong())
        baseCreative.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                target.visibility = View.GONE
            }
        })
        baseCreative.startAnimationTogether()
    }


