package com.icostel.facerecognition.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import com.icostel.facerecognition.R

class FaceOverlay(context: Context,
                  graphicOverlay: GraphicOverlay,
                  private val rect: Rect) : GraphicOverlay.Graphic(graphicOverlay) {

    private val rectPaint = Paint()

    init {
        rectPaint.color = context.resources.getColor(R.color.colorAccent_20)
        rectPaint.style = Paint.Style.FILL
        rectPaint.flags = Paint.ANTI_ALIAS_FLAG
        postInvalidate()
    }


    public override fun draw(canvas: Canvas) {
        val rectF = RectF(rect)

        rectF.left = translateX(rectF.left)
        rectF.right = translateX(rectF.right)
        rectF.top = translateY(rectF.top)
        rectF.bottom = translateY(rectF.bottom)

        canvas.drawOval(rectF, rectPaint)
    }
}
