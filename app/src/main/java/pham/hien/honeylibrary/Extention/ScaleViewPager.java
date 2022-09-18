package pham.hien.honeylibrary.Extention;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class ScaleViewPager extends ViewPager implements ViewPager.PageTransformer {
    public static final String TAG = ScaleViewPager.class.getName();
    private float MAX_SCALE = 0.0f;
    private int mPageMargin;
    private boolean animationEnabled = true;
    private boolean fadeEnabled = false;
    private float fadeFactor = 0.5f;
    private float numPadding = 3f;

    public ScaleViewPager(Context context) {
        this(context, null);
    }

    public ScaleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // clipping should be off on the pager for its children so that they can scale out of bounds.
        setClipChildren(false);
        setClipToPadding(false);
        // to avoid fade effect at the end of the page
        setOverScrollMode(2);
        setPageTransformer(false, this);
        setOffscreenPageLimit(3);
        mPageMargin = dp2px(context.getResources(), 30);
        setPadding(mPageMargin, (int) (mPageMargin / numPadding), mPageMargin, (int) (mPageMargin / numPadding));
    }

    public int dp2px(Resources resource, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics());
    }

    public void setAnimationEnabled(boolean enable) {
        this.animationEnabled = enable;
    }

    public void setFadeEnabled(boolean fadeEnabled) {
        this.fadeEnabled = fadeEnabled;
    }

    public void setFadeFactor(float fadeFactor) {
        this.fadeFactor = fadeFactor;
    }

    @Override
    public void setPageMargin(int marginPixels) {
        mPageMargin = marginPixels;
        setPadding(mPageMargin, (int) (mPageMargin / numPadding), mPageMargin, (int) (mPageMargin / numPadding));
    }


    @Override
    public void transformPage(View page, float position) {
        if (mPageMargin <= 0 || !animationEnabled)
            return;

        page.setPadding((int) (mPageMargin / numPadding), (int) (mPageMargin / numPadding), (int) (mPageMargin / numPadding), (int) (mPageMargin / numPadding));

        if (MAX_SCALE == 0.0f && position > 0.0f && position < 1.0f) {
            MAX_SCALE = position;
        }
        position = position - MAX_SCALE;
        float absolutePosition = Math.abs(position);
        if (position <= -1.0f || position >= 1.0f) {
            if (fadeEnabled)
                page.setAlpha(fadeFactor);
            // Page is not visible -- stop any running animations

        } else if (position == 0.0f) {
            // Page is selected -- reset any views if necessary
            page.setScaleX((1 + MAX_SCALE));
            page.setScaleY((1 + MAX_SCALE));
            page.setAlpha(1);
        } else {
            page.setScaleX(1 + MAX_SCALE * (1 - absolutePosition));
            page.setScaleY(1 + MAX_SCALE * (1 - absolutePosition));
            if (fadeEnabled)
                page.setAlpha(Math.max(fadeFactor, 1 - absolutePosition));
        }

    }

    private boolean isPagerEnabled = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isPagerEnabled) {
            try {
                return super.onTouchEvent(event);
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isPagerEnabled) {
            try {
                return super.onInterceptTouchEvent(event);
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void setPagerEnabled(boolean enabled) {
        this.isPagerEnabled = enabled;
    }

    public void setDurationScroll(int millis) {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new OwnScroller(getContext(), millis));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class OwnScroller extends Scroller {

        private int durationScrollMillis = 250;

        public OwnScroller(Context context, int durationScroll) {
            super(context, new DecelerateInterpolator());
            this.durationScrollMillis = durationScroll;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, durationScrollMillis);
        }
    }
}
