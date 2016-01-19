package com.ewhappcenter.saljjak.font;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontHelper {
    public enum FontType{
        BOLD, DEFAULT
    }

    private static FontHelper instance;

    public static FontHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (FontHelper.class) {
                if (instance == null) {
                    instance = new FontHelper(context);
                }
            }
        }

        return instance;
    }

    private final Typeface typeBold;

    protected FontHelper(Context context) {
        typeBold = Typeface.createFromAsset(context.getAssets(), "SHANB.OTF");
    }

    private void setType(TextView targetTextView, FontType type) {
        switch (type) {
            case BOLD:
                targetTextView.setTypeface(typeBold);
        }
    }
}
