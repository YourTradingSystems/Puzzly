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
    private Point position;
    private int resizeWidth = 0;
    private int resizeHeight = 0;

    private final ParseSvgAsyncTask.ParseListener parseListener = new ParseSvgAsyncTask.ParseListener() {
        @Override
        public void onParseDone(Drawable drawable) {
            setImageDrawable(drawable);
            resizeHeight = Math.round(drawable.getIntrinsicHeight() / baseHeight * screenSize.y);
            resizeWidth = Math.round(resizeHeight / (float)drawable.getIntrinsicHeight()  * drawable.getIntrinsicWidth());
            resizeFigurePosition(position, drawable.getIntrinsicWidth(), resizeWidth);
        }
    };

    public AutoResizeImageView(Context context) {
        super(context);
        initializeScreenSize();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public AutoResizeImageView(Context context, Point _position) {
        super(context);
        position = _position;
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

    public void setImageFromNameInThread(String name) {
        ParseSvgAsyncTask parseSvgAsyncTask = new ParseSvgAsyncTask(getContext(), parseListener);
        parseSvgAsyncTask.execute(name);
    }

    public void resizeFigurePosition(Point oldPos, int oldFigureWidth, int newFigureWidth){
        int newPosY = Math.round(oldPos.y / AutoResizeImageView.baseHeight * screenSize.y);
        int newPosX = Math.round((oldPos.x + oldFigureWidth / 2)  / AutoResizeImageView.baseWidth * screenSize.x - newFigureWidth / 2);
        setX(newPosX);
        setY(newPosY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resizeWidth, resizeHeight);
    }
}
