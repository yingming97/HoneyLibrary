package pham.hien.honeylibrary.Extention;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import pham.hien.honeylibrary.Utils.TypefaceUtils;

public class EditTextPoppinsMedium extends AppCompatEditText {

    public EditTextPoppinsMedium(Context context) {
        super(context);
        initFont(context);
    }

    public EditTextPoppinsMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont(context);
    }

    private void initFont(Context context) {
        setTypeface(TypefaceUtils.Companion.getMedium(context.getAssets()));
    }
}
