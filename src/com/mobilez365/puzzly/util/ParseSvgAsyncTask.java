package com.mobilez365.puzzly.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.os.AsyncTask;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import java.nio.channels.Pipe;

/**
 * Created by andrewtivodar on 28.05.2014.
 */
public class ParseSvgAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private Context context;
    private ParseListener listener;
    private int maxWidth;
    private int maxHeight;

    interface ParseListener{
        public void onParseDone(Bitmap bitmap);
    }

    public ParseSvgAsyncTask(Context _context, ParseListener _listener, int _width, int _height) {
        listener = _listener;
        context = _context;
        maxWidth = _width;
        maxHeight = _height;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        SVG svg = SVGParser.getSVGFromResource(context.getResources(), getResIdFromString(params[0]));
        Picture picture = svg.getPicture();
        int width = (int) ((maxHeight / (float) picture.getHeight()) * picture.getWidth());
        Bitmap bmp = Bitmap.createBitmap(width, maxHeight, Bitmap.Config.ARGB_8888);
        Canvas cnv = new Canvas(bmp);
        cnv.drawPicture(picture, new Rect(0, 0, width, maxHeight));

        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        listener.onParseDone(result);
    }


    private int getResIdFromString(String name) {
        return context.getResources().getIdentifier(name, "raw", context.getPackageName());
    }

}
