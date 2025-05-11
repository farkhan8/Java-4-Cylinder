package com.example.tabung;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Path;
import android.graphics.DashPathEffect;
import android.util.AttributeSet;
import android.view.View;

public class CylinderView extends View {
    private Paint paint;
    private Paint textPaint;
    private Paint dottedPaint;
    private float radius = 100;
    private float height = 200;
    private static final float PADDING = 50;  // Padding around the drawing

    public CylinderView(Context context) {
        super(context);
        init();
    }

    public CylinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CylinderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);

        dottedPaint = new Paint();
        dottedPaint.setAntiAlias(true);
        dottedPaint.setColor(Color.GRAY);
        dottedPaint.setStyle(Paint.Style.STROKE);
        dottedPaint.setStrokeWidth(2);
        dottedPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
    }

    public void setDimensions(float radius, float height) {
        this.radius = radius;
        this.height = height;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        
        // Calculate scale to fit the view with padding
        float maxRadius = (getWidth() - 2 * PADDING) / 3;  // Allow space for measurements
        float maxHeight = (getHeight() - 2 * PADDING) / 1.5f;
        float scale = Math.min(maxRadius / radius, maxHeight / height);
        
        // Scale dimensions for display
        float displayRadius = radius * scale;
        float displayHeight = height * scale;

        // Center the cylinder vertically and horizontally
        float cylinderLeft = centerX - displayRadius;
        float cylinderRight = centerX + displayRadius;
        float cylinderTop = centerY - displayHeight/2;
        float cylinderBottom = centerY + displayHeight/2;

        // Draw the cylinder body
        paint.setColor(Color.rgb(144, 238, 144)); // Light green color
        paint.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(cylinderLeft, cylinderTop, cylinderRight, cylinderBottom);
        canvas.drawRect(rect, paint);

        // Draw the bottom ellipse first (it will be partially hidden)
        RectF bottomOval = new RectF(cylinderLeft, cylinderBottom - displayRadius*0.2f,
                                    cylinderRight, cylinderBottom + displayRadius*0.2f);
        canvas.drawOval(bottomOval, paint);

        // Draw the top ellipse
        RectF topOval = new RectF(cylinderLeft, cylinderTop - displayRadius*0.2f,
                                 cylinderRight, cylinderTop + displayRadius*0.2f);
        canvas.drawOval(topOval, paint);

        // Draw outlines
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        
        // Draw the visible part of bottom ellipse outline
        Path bottomEllipsePath = new Path();
        bottomEllipsePath.addArc(bottomOval, 0, 180);
        canvas.drawPath(bottomEllipsePath, paint);
        
        // Draw the rectangle outline
        canvas.drawLine(cylinderLeft, cylinderTop, cylinderLeft, cylinderBottom, paint);
        canvas.drawLine(cylinderRight, cylinderTop, cylinderRight, cylinderBottom, paint);
        
        // Draw the top ellipse outline
        canvas.drawOval(topOval, paint);

        // Draw radius measurement
        paint.setColor(Color.RED); // Red color for measurements like in the image
        paint.setStrokeWidth(3);
        canvas.drawLine(centerX, cylinderTop, cylinderRight, cylinderTop, paint);

        // Draw height measurement
        canvas.drawLine(cylinderRight + 10, cylinderTop, cylinderRight + 10, cylinderBottom, paint);

        // Draw measurement labels
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        
        // Radius label
        String radiusLabel = String.format("%.0f cm", radius);
        canvas.drawText(radiusLabel, 
                       (centerX + cylinderRight) / 2, 
                       cylinderTop - 20, 
                       textPaint);

        // Height label
        String heightLabel = String.format("%.0f cm", height);
        canvas.save();
        canvas.rotate(90, cylinderRight + 50, (cylinderTop + cylinderBottom) / 2);
        canvas.drawText(heightLabel, 
                       cylinderRight + 50, 
                       (cylinderTop + cylinderBottom) / 2, 
                       textPaint);
        canvas.restore();
    }
}
