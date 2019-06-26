package com.icostel.facerecognition.ui.views;

import android.graphics.*;

public class RectOverlay extends GraphicOverlay.Graphic {

    private static final int RECT_COLOR = Color.RED;
    private static final float STROKE_WIDTH = 4.0f;

    private Paint rectPaint;
    private Rect rect;

    public RectOverlay(GraphicOverlay graphicOverlay, Rect rect) {
        super(graphicOverlay);

        this.rect = rect;
        rectPaint = new Paint();
        rectPaint.setColor(RECT_COLOR);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(STROKE_WIDTH);

        postInvalidate();
    }


    @Override
    public void draw(Canvas canvas) {
        RectF rectF = new RectF(rect);
        rectF.left = translateX(rectF.left);
        rectF.right = translateX(rectF.right);
        rectF.top = translateY(rectF.top);
        rectF.bottom = translateY(rectF.bottom);

        canvas.drawRect(rectF, rectPaint);
    }
}
