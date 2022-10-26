package pham.hien.honeylibrary.Extention;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import pham.hien.honeylibrary.R;
import pham.hien.honeylibrary.Utils.MoneyFormatterKt;

public class CustomMarkerChartView extends MarkerView implements IMarker {

    Context mContext;
    private final TextView txv_market_chart_view__value;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomMarkerChartView(Context context, int layoutResource) {
        super(context, layoutResource);
        txv_market_chart_view__value = findViewById(R.id.txv_market_chart_view__value);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        txv_market_chart_view__value.setText(MoneyFormatterKt.moneyFormatter((int) e.getY()));
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }
}
