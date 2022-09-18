package pham.hien.honeylibrary.Extention;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import pham.hien.honeylibrary.Utils.TypefaceUtils;

public class TextViewPoppinsMedium extends AppCompatTextView {

    public TextViewPoppinsMedium(Context context) {
        super(context);
        initFont(context);
    }

    public TextViewPoppinsMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont(context);
    }

    private void initFont(Context context) {
        setTypeface(TypefaceUtils.Companion.getMedium(context.getAssets()));
    }
}
