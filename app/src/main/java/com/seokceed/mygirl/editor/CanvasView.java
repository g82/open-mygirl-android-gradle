package com.seokceed.mygirl.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gamepari on 3/4/15.
 */
public class CanvasView extends View implements View.OnTouchListener {

    GestureDetector gestureDetector;
    private Bitmap mBackground;
    private boolean isInited = false;
    private Paint mPaint;
    private Paint drawPaint;
    private float previousX;
    private float previousY;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initBackground(Bitmap bitmap) {
        mBackground = bitmap;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        drawPaint.setColor(Color.BLACK);

        gestureDetector = new GestureDetector(getContext(), new mListener());

        isInited = true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInited) {
            canvas.drawBitmap(mBackground, 0, 0, mPaint);

            canvas.drawCircle(previousX, previousY, 10.f, drawPaint);


            canvas.drawPoint(previousX, previousY, drawPaint);

            invalidate();

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    class mListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("CanvasView", "onScroll");

            previousX = e2.getX();
            previousY = e2.getY();

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("CanvasView", "onDown");
            return super.onDown(e);
        }
    }
}
