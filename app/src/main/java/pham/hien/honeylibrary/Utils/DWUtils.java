package pham.hien.honeylibrary.Utils;

import android.content.Context;

import pham.hien.honeylibrary.Extention.ObservableHorizontalScrollView;


public class DWUtils {
    private static final String TAG = DWUtils.class.getName();

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float getRulerViewValue(ObservableHorizontalScrollView view, int l, int t, float MAX_VALUE, float MIN_VALUE) {

        float oneValue = (float) view.getWidth() * 3f / (MAX_VALUE - MIN_VALUE);
        int value = (int) (l / oneValue) + (int) MIN_VALUE;

        if (value > (MAX_VALUE)) value = (int) MAX_VALUE;
        else if (value < MIN_VALUE) value = (int) MIN_VALUE;

        return value;
    }

    public static int getValueCenter(ObservableHorizontalScrollView view, int l, int t, float MAX_VALUE, float MIN_VALUE, float multipleSize, int valueMultiple) {
        float oneValue = (float) view.getWidth() * multipleSize / (MAX_VALUE - MIN_VALUE);
        int value = (int) (l / oneValue) + (int) MIN_VALUE;
        int offset = (int) (l % oneValue);
        if (offset > oneValue / 2) {
            value += 1;
        }
        if (value > MAX_VALUE) {
            value = (int) MAX_VALUE;
        }
        return value * valueMultiple;
    }

//    private static ValueAndScrollItemToCenterTask mValueAndScrollItemToCenterTask;
//    private static int valueCenter = 0;
//
//    public static class ValueAndScrollItemToCenterTask extends AsyncTask<Void, Void, Integer> {
//        private ObservableHorizontalScrollView view;
//        private int l;
//        private int t;
//        private float MAX_VALUE;
//        private float MIN_VALUE;
//        private float multipleSize;
//
//        public ValueAndScrollItemToCenterTask(ObservableHorizontalScrollView view, int l, int t, float MAX_VALUE, float MIN_VALUE, float multipleSize, int valueMultiple) {
//            this.view = view;
//            this.l = l;
//            this.t = t;
//            this.MAX_VALUE = MAX_VALUE;
//            this.MIN_VALUE = MIN_VALUE;
//            this.multipleSize = multipleSize;
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            return getValueAndScrollItemToCenter(view, l, t, MAX_VALUE, MIN_VALUE, multipleSize, 1);
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//            valueCenter = integer;
//            Log.e(TAG, "onPostExecute: " + integer);
//        }
//    }

//    public static int getValueAndScrollItemToCenter(ObservableHorizontalScrollView view, int l, int t, float MAX_VALUE, float MIN_VALUE, float multipleSize) {
//        if (mValueAndScrollItemToCenterTask == null || mValueAndScrollItemToCenterTask.getStatus() == AsyncTask.Status.FINISHED) {
//            mValueAndScrollItemToCenterTask = new ValueAndScrollItemToCenterTask(view, l, t, MAX_VALUE, MIN_VALUE, multipleSize, 1);
//            mValueAndScrollItemToCenterTask.execute();
//        }
//        return valueCenter;
//    }

    public static int getValueAndScrollItemToCenter(ObservableHorizontalScrollView view, int l, int t, float MAX_VALUE, float MIN_VALUE, float multipleSize) {
        return getValueAndScrollItemToCenter(view, l, t, MAX_VALUE, MIN_VALUE, multipleSize, 1);
    }

    public static int getValueAndScrollItemToCenter(ObservableHorizontalScrollView view, int l, int t, float MAX_VALUE, float MIN_VALUE, float multipleSize, int valueMultiple) {
        float oneValue = (float) view.getWidth() * multipleSize / (MAX_VALUE - MIN_VALUE);
        int value = (int) (l / oneValue) + (int) MIN_VALUE;
        int offset = (int) (l % oneValue);


        if (offset > oneValue / 2) {
            value += 1;
            view.smoothScrollBy((int) oneValue - offset, 0);

        } else {
            view.smoothScrollBy(-offset, 0);
        }

        if (value > MAX_VALUE) {
            value = (int) MAX_VALUE;
        }

        return value * valueMultiple;
    }

    public static void scrollToValue(ObservableHorizontalScrollView view, float value, float MAX_VALUE, float MIN_VALUE, float multipleSize) {
        float oneValue = (float) view.getWidth() * multipleSize / (MAX_VALUE - MIN_VALUE);
        float valueWidth = oneValue * (value - MIN_VALUE);

        view.scrollBy((int) valueWidth, 0);
    }

//    public static void scrollToValue(ObservableHorizontalScrollView view,int width, float value, float MAX_VALUE, float MIN_VALUE) {
//        float oneValue = (float) width / (MAX_VALUE - MIN_VALUE);
//        float valueWidth = oneValue * (value - MIN_VALUE);
//
//        view.scrollBy((int) valueWidth, 0);
//    }
}
