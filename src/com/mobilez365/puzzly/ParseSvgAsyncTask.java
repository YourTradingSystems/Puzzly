package com.mobilez365.puzzly;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.AsyncTask;
import android.util.Log;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.nio.channels.Pipe;

/**
 * Created by andrewtivodar on 28.05.2014.
 */
public class ParseSvgAsyncTask extends AsyncTask<String, Void, Drawable> {

    private Context context;
    private ParseListener listener;

    public interface ParseListener{
        public void onParseDone(Drawable drawable);
    }

    public ParseSvgAsyncTask(Context _context, ParseListener _listener) {
        listener = _listener;
        context = _context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Drawable doInBackground(String... params) {
        SVG svg = null;
        try {
            svg = SVG.getFromResource(context, getResIdFromString(params[0]));
        } catch (SVGParseException e) {
        }
        Drawable drawable = new PictureDrawable(svg.renderToPicture());
        return drawable;
    }

    @Override
    protected void onPostExecute(Drawable result) {
        super.onPostExecute(result);
        listener.onParseDone(result);
    }

    private int getResIdFromString(String name) {
        return context.getResources().getIdentifier(name, "raw", context.getPackageName());
    }

}
