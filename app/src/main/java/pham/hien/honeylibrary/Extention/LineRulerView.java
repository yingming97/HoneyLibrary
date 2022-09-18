package pham.hien.honeylibrary.Extention;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import pham.hien.honeylibrary.R;

public class LineRulerView extends View {
    private static final String TAG = LineRulerView.class.getName();
    private Context mContext;
    private Paint paint;
    private Paint paintTitle;
    private Paint textPaint;
    private DashPathEffect dashPathEffect;
    private Path backGroundPath;

    private float bigUnitLineHeight = 0f;
    private float samllUnitLineHeight = 0f;

    private float MAX_DATA = 100;
    private float MIN_DATA = 0;

    private int viewHeight = 0;
    private int viewWidth = 0;

    private int showRangeValue = 5;

    private int valueMultiple = 1;

    private int displayNumberType = 1;

    public static final int DISPLAY_NUMBER_TYPE_SPACIAL_COUNT = 1;
    public static final int DISPLAY_NUMBER_TYPE_MULTIPLE = 2;
    private int valueTypeMultiple = 5;

    private int longHeightRatio = 20;
    private int shortHeightRatio = 12;
    private int baseHeightRatio = 3;


    public LineRulerView(Context context) {
        super(context);
        init(context);
    }

    public LineRulerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LineRulerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LineRulerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        paint = new Paint();
        paint.setColor(Color.parseColor("#BDB6FF"));
        paint.setStrokeWidth(4f);
        paint.isAntiAlias();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        paintTitle = new Paint();
        paintTitle.setColor(Color.parseColor("#7265E3"));
        paintTitle.setStrokeWidth(4f);
        paintTitle.isAntiAlias();
        paintTitle.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#58658C"));
        textPaint.isAntiAlias();
        textPaint.setTextSize(context.getResources().getDimension(R.dimen._12sdp));
        textPaint.setTextAlign(Paint.Align.CENTER);

        invalidate();
    }

    public LineRulerView setMaxValue(float maxValue) {
        this.MAX_DATA = maxValue;
        return this;
    }

    public LineRulerView setMinValue(float minValue) {
        this.MIN_DATA = minValue;
        return this;
    }

    public LineRulerView setMinMaxValue(float minValue, float maxValue) {
        this.MIN_DATA = minValue;
        this.MAX_DATA = maxValue;
        return this;
    }

    public LineRulerView setValueMultiple(int valueMultiple) {
        this.valueMultiple = valueMultiple;
        return this;
    }

    public void setMultipleTypeValue(int valueTypeMultiple) {
        this.displayNumberType = DISPLAY_NUMBER_TYPE_MULTIPLE;
        this.valueTypeMultiple = valueTypeMultiple;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        viewHeight = getMeasuredHeight();
        viewWidth = getMeasuredWidth();

        float viewInterval = (float) viewWidth / (MAX_DATA - MIN_DATA);

        for (int i = 0; i <= (MAX_DATA - MIN_DATA); i++) {
            if (displayNumberType == DISPLAY_NUMBER_TYPE_MULTIPLE) {
                if (((int) (i + MIN_DATA) * valueMultiple) % valueTypeMultiple == 0) {
                    canvas.drawLine(viewInterval * i, 0, viewInterval * i, (viewHeight / shortHeightRatio) * baseHeightRatio, paint);
                    if (i == 0) {
                        canvas.drawText("" + ((int) (i + MIN_DATA) * valueMultiple), (viewInterval * i) + mContext.getResources().getDimension(R.dimen._6sdp), (viewHeight / shortHeightRatio) * baseHeightRatio + mContext.getResources().getDimension(R.dimen._12sdp), textPaint);
                    } else if (i == (MAX_DATA - MIN_DATA)) {
                        canvas.drawText("" + ((int) (i + MIN_DATA) * valueMultiple), (viewInterval * i) - mContext.getResources().getDimension(R.dimen._11sdp), (viewHeight / shortHeightRatio) * baseHeightRatio + mContext.getResources().getDimension(R.dimen._12sdp), textPaint);
                    } else {
                        canvas.drawText("" + ((int) (i + MIN_DATA) * valueMultiple), viewInterval * i, (viewHeight / shortHeightRatio) * baseHeightRatio + mContext.getResources().getDimension(R.dimen._12sdp), textPaint);
                    }
                } else {
                    canvas.drawLine(viewInterval * i, 0, viewInterval * i, viewHeight / longHeightRatio * baseHeightRatio, paint);
                }
            } else {
//                if (i % 10 == 0) {
                if (i % 5 == 0) {
                    int startY = (viewHeight - ((viewHeight / shortHeightRatio) * baseHeightRatio)) / 2;
                    int endY = viewHeight - startY;
                    canvas.drawLine(viewInterval * i, startY, viewInterval * i, endY, paintTitle);
                    if (i == 0) {
                        canvas.drawText("" + ((int) (i + MIN_DATA) * valueMultiple), ((viewInterval * i) + mContext.getResources().getDimension(R.dimen._6sdp)), endY + mContext.getResources().getDimension(R.dimen._28sdp), textPaint);
                    } else if (i == (MAX_DATA - MIN_DATA)) {
                        canvas.drawText("" + ((int) (i + MIN_DATA) * valueMultiple), ((viewInterval * i) - mContext.getResources().getDimension(R.dimen._10sdp)), endY + mContext.getResources().getDimension(R.dimen._28sdp), textPaint);
                    } else {
                        canvas.drawText("" + ((int) (i + MIN_DATA) * valueMultiple), viewInterval * i, endY + mContext.getResources().getDimension(R.dimen._28sdp), textPaint);
                    }
//                } else if (i % 5 == 0) {
//                    int startY = (viewHeight - ((viewHeight / shortHeightRatio) * baseHeightRatio)) / 2;
//                    int endY = viewHeight - startY;
//                    canvas.drawLine(viewInterval * i, startY, viewInterval * i, endY, paint);
                } else {
                    int startY = (viewHeight - ((viewHeight / longHeightRatio) * baseHeightRatio)) / 2;
                    int endY = viewHeight - startY;
                    canvas.drawLine(viewInterval * i, startY, viewInterval * i, endY, paint);
                }
            }
        }
        super.onDraw(canvas);
    }
}
