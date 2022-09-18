package pham.hien.honeylibrary.Extention;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;

import pham.hien.honeylibrary.R;

public class SeekBarProgressDrawable extends ClipDrawable {
    private String TAG = SeekBarProgressDrawable.class.getName();
    private Paint mPaint = new Paint();
    private Rect mRect;
    private Context mContext;


    public SeekBarProgressDrawable(Drawable drawable, int gravity, int orientation, Context ctx) {
        super(drawable, gravity, orientation);
        mContext = ctx;

    }

    @Override
    public void draw(Canvas canvas) {
//        if (mRect == null) {
//            mRect = new Rect(getBounds().left, (int) (getBounds().centerY() - dy / 2), getBounds().right, (int) (getBounds().centerY() + dy / 2));\
            float radius = mContext.getResources().getDimension(R.dimen._2sdp);
            Path clipPath = new Path();
            clipPath.addRoundRect(new RectF(canvas.getClipBounds()), radius, radius, Path.Direction.CW);
            canvas.clipPath(clipPath);
//        }

        super.draw(canvas);
    }


    @Override
    public void setAlpha(int i) {
        mPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
