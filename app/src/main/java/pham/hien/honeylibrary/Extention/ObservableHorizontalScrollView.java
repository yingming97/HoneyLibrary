package pham.hien.honeylibrary.Extention;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ObservableHorizontalScrollView extends HorizontalScrollView {

    private Runnable scrollerTask;
    private int initialPosition;

    private int newCheck = 100;

    private float initValue = 0f;

    public interface OnScrollChangedListener {
        void onScrollChanged(ObservableHorizontalScrollView view, int l, int t);

        void onScrollStopped(int l, int t);
    }


    private OnScrollChangedListener mOnScrollChangedListener;

    public ObservableHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ObservableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void setOnScrollChangedListener(OnScrollChangedListener l) {
        mOnScrollChangedListener = l;
    }


    private void init() {
        scrollerTask = new Runnable() {
            public void run() {

                int newPosition = getScrollX();
                if (initialPosition - newPosition == 0) {//has stopped

                    if (mOnScrollChangedListener != null) {
                        mOnScrollChangedListener.onScrollStopped(getScrollX(), getScrollY());
                    }
                } else {
                    initialPosition = getScrollX();
                    ObservableHorizontalScrollView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t);
        }
    }

    public void startScrollerTask() {
        initialPosition = getScrollY();
        ObservableHorizontalScrollView.this.postDelayed(scrollerTask, newCheck);
    }
}