package com.example.sherman.sbook.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by kenp on 12/11/2016.
 */

public class FontAwesomeTextView extends TextView {
    public FontAwesomeTextView(Context context) {
        super(context);
        setAwesomeFont();
    }

    public FontAwesomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAwesomeFont();
    }

    public FontAwesomeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAwesomeFont();
    }

    public FontAwesomeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAwesomeFont();
    }

    private void setAwesomeFont() {
        try {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fontawesome.ttf");
            setTypeface(typeface);
        } catch (Exception e) {
            Log.e("FONTAWESOME", "setAwesomeFont: " + e.getMessage());
        }

    }
}
