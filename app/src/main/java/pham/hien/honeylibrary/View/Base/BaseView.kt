package pham.hien.honeylibrary.View.Base

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import pham.hien.honeylibrary.Animation.AlphaAnimation

abstract class BaseView : RelativeLayout, View.OnClickListener {


    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    open fun initView(context: Context?, attrs: AttributeSet?) {}
    open fun initViewModel(viewModel: ViewModel?) {}
    open fun initObserver(owner: LifecycleOwner?) {}
    open fun initDataDefault(activity: Activity?) {}
    open fun openForTheFirstTime(activity: Activity?) {}

    override fun onClick(view: View) {}

    companion object {

        fun openViewGroup(viewGroup: ViewGroup, duration: Int) {
            if (viewGroup.visibility == GONE) {
                AlphaAnimation().visibleAnimation(
                    viewGroup,
                    duration.toLong(),
                    object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {
                            viewGroup.visibility = VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animator) {}
                        override fun onAnimationCancel(animation: Animator) {}
                        override fun onAnimationRepeat(animation: Animator) {}
                    })
            }
        }

        fun closeViewGroup(viewGroup: ViewGroup, duration: Int) {
            AlphaAnimation().goneAnimation(
                viewGroup,
                duration.toLong(),
                object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        viewGroup.visibility = GONE
                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
        }

        fun closeViewGroup(viewGroup: ViewGroup) {
            AlphaAnimation().goneAnimation(viewGroup, 400, object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    viewGroup.visibility = GONE
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }

        fun isOpening(viewGroup: ViewGroup): Boolean {
            return viewGroup.visibility == VISIBLE
        }
    }
}
