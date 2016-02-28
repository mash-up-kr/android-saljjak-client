package com.ewhappcenter.saljjak;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.InputStream;

public class GifView extends View {

    public InputStream gifInputStream;
    private Movie gifMovie;
    float screenWidth, screenHeight;
    private int movieWidth, movieHeight;
    private long movieDuration;
    private long movieStart;
    private int gifname = +R.drawable.saljjak_splash;

    public GifView(Context context) {
        super(context);
        init();
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true);
        gifInputStream = getResources().openRawResource(gifname);
        gifMovie = Movie.decodeStream(gifInputStream);


        movieWidth = gifMovie.width();
        movieHeight = gifMovie.height();
        movieDuration = gifMovie.duration();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    public int getMovieWidth() {
        return movieWidth;
    }

    public int getMovieHeight() {
        return movieHeight;
    }

    public long getMovieDuration() {
        return movieDuration;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        long now = SystemClock.uptimeMillis();

        if(movieStart == 0) {
            movieStart = now;
        }

        if(gifMovie != null) {

            int dur = gifMovie.duration();
            if(dur == 0) {
                dur = 1000;
            }

            int relTime = (int)((now - movieStart) % dur);

            gifMovie.setTime(relTime);

            Display dis = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            screenWidth = dis.getWidth();
            screenHeight = dis.getHeight();
            float scale_width = screenWidth/movieWidth;
            float scale_height = screenHeight/movieHeight;
            canvas.scale(scale_width, scale_height);
            gifMovie.draw(canvas,0, 0);

            invalidate();
        }
    }

}
