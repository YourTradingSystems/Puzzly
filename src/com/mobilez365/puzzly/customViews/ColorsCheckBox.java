package com.mobilez365.puzzly.customViews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.mobilez365.puzzly.R;

import java.util.Random;

/**
 * Created by Denis on 20.05.14.
 */
public class ColorsCheckBox extends CheckBox {

    private Context mContext;

    public ColorsCheckBox(Context _context) {
        super(_context);
        mContext = _context;
        setButtonDrawable(getRandomCheckBoxDrawable(false));
    }

    public ColorsCheckBox(Context _context, AttributeSet attrs, int defStyle) {
        super(_context, attrs, defStyle);
        mContext = _context;
        setButtonDrawable(getRandomCheckBoxDrawable(false));
    }

    public ColorsCheckBox(Context _context, AttributeSet attrs) {
        super(_context, attrs);
        mContext = _context;
        setButtonDrawable(getRandomCheckBoxDrawable(false));
    }

    @Override
    public void setChecked(boolean _checked) {
        super.setChecked(_checked);
        setButtonDrawable(getRandomCheckBoxDrawable(_checked));
    }

    private final Drawable getRandomCheckBoxDrawable(Boolean _checked) {
        Random rand = new Random();
        String drawableName[] = new String[] {"check1_icon", "check2_icon", "check3_icon", "check4_icon", "check5_icon"};
        int id = 0;

        if (_checked)
            id = getResources().getIdentifier(drawableName[rand.nextInt(drawableName.length)], "drawable", mContext.getPackageName());
        else
            id = R.drawable.check_null_icon;

        return getResources().getDrawable(id);
    }
}
