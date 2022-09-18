package pham.hien.honeylibrary.Extention;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import pham.hien.honeylibrary.Utils.TypefaceUtils;

public class TextViewPoppinsRegular extends AppCompatTextView {

    public TextViewPoppinsRegular(Context context) {
        super(context);
        initFont(context);
    }

    public TextViewPoppinsRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont(context);
    }

    private void initFont(Context context) {
        setTypeface(TypefaceUtils.Companion.getRegular(context.getAssets()));
    }
}

