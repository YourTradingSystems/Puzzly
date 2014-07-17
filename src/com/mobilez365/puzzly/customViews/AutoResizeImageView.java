package com.mobilez365.puzzly.customViews;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.mobilez365.puzzly.ParseSvgAsyncTask;

/**
 * Created by andrewtivodar on 15.07.2014.
 */
public class AutoResizeImageView extends ImageView {
    public static final float baseWidth = 800;
    public static final float baseHeight = 480;
    private static Point screenSize;
    private int resizeWidth = 0;
    private int resizeHeight = 0;

    private final ParseSvgAsyncTask.ParseListener parseListener = new ParseSvgAsyncTask.ParseListener() {
        @Override
        public void onParseDone(Drawable drawable) {
            setImageDrawable(drawable);
            resizeHeight = Math.round(drawable.getIntrinsicHeight() / baseHeight * screenSize.y);
            resizeWidth = Math.round(resizeHeight / (float)drawable.getIntrinsicHeight()  * drawable.getIntrinsicWidth());

        }
    };

    public AutoResizeImageView(Context context) {
        super(context);
        initializeScreenSize();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public AutoResizeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeScreenSize();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public AutoResizeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeScreenSize();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void initializeScreenSize(){
        if(screenSize == null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenSize = size;
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        SVG svg = null;
        try {
            svg = SVG.getFromResource(getContext(), resId);
        } catch (SVGParseException e) {
        }
        Drawable drawable = new PictureDrawable(svg.renderToPicture());
        setImageDrawable(drawable);

        resizeWidth = (int) (drawable.getIntrinsicWidth() / baseWidth * screenSize.x);
        resizeHeight = (int) (drawable.getIntrinsicHeight() / baseHeight * screenSize.y);
    }

    public void setImageFromNameInThread(String name) {
        ParseSvgAsyncTask parseSvgAsyncTask = new ParseSvgAsyncTask(getContext(), parseListener);
        parseSvgAsyncTask.execute(name);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resizeWidth, resizeHeight);
    }
}
