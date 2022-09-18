package pham.hien.honeylibrary.Extention;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import pham.hien.honeylibrary.Utils.TypefaceUtils;

public class TextViewPoppinsSemiBold extends AppCompatTextView {

    public TextViewPoppinsSemiBold(Context context) {
        super(context);
        initFont(context);
    }

    public TextViewPoppinsSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont(context);
    }

    private void initFont(Context context) {
        setTypeface(TypefaceUtils.Companion.getSemiBold(context.getAssets()));
    }
}
