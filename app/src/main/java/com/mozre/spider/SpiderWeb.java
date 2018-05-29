package com.mozre.spider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;


public class SpiderWeb extends View {
    private static final String TAG = "SpiderWeb";
    private Paint mPaint = new Paint();
    private int mCenterWidth;
    private int mCenterHeight;
    private float mCircleRadius;
    private float mRateCircleWeight = 0.7f;
    private int mDataCount;
    private float mAngle;
    private List<SpiderData> mData;
    private String mTextColor = "#000000";
    private String mSpiderWebColor = "#888888";
    private String mDataAreaColor = "#0099FF";
    private String mDataPointColor = "#0066FF";
    private int mDataNamePadding = 50;
    private int mDataNameTextSize = 50;
    private float mDataAreaAlpha = 0.6f;

    public void updateData(List<SpiderData> data) {
        this.mData = data;
        mDataCount = data.size();
        mAngle = (float) Math.PI * 2 / mDataCount;
        invalidate();
    }


    public SpiderWeb(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public SpiderWeb(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public SpiderWeb(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterWidth = w / 2;
        mCenterHeight = h / 2;
        mCircleRadius = Math.min(w, h) / 2 * mRateCircleWeight / mDataCount;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        drawSpiderWeb(canvas);
        drawDivideLine(canvas);
        drawAreaName(canvas, mData);
        drawDataArea(canvas, mData);
    }

    private void initPaint() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
        mPaint.setColor(Color.BLUE);
    }

    private void drawSpiderWeb(Canvas canvas) {
        Path path = new Path();
        mPaint.setColor(Color.parseColor(mSpiderWebColor));
        for (int j = 0; j < mDataCount; ++j) {
            path.reset();
            path.moveTo(mCenterWidth + mCircleRadius * j, mCenterHeight);
            for (int i = 1; i < mDataCount; ++i) {
                float x = (float) (mCenterWidth + mCircleRadius * j * Math.cos(mAngle * i));
                float y = (float) (mCenterHeight + mCircleRadius * j * Math.sin(mAngle * i));
                path.lineTo(x, y);
            }
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    private void drawDivideLine(Canvas canvas) {
        float currentCircle = mCircleRadius * (mDataCount - 1);
        float currentAngle = 0;
        Path path = new Path();
        float x;
        float y;
        mPaint.setColor(Color.parseColor(mSpiderWebColor));
        for (int i = 0; i < mDataCount; ++i) {
            path.reset();
            currentAngle = mAngle * i;
            x = (float) (mCenterWidth + currentCircle * Math.cos(currentAngle));
            y = (float) (mCenterHeight + currentCircle * Math.sin(currentAngle));
            path.moveTo(mCenterWidth, mCenterHeight);
            path.lineTo(x, y);
            canvas.drawPath(path, mPaint);
        }
    }

    private void drawAreaName(Canvas canvas, List<SpiderData> data) {
        float currentCircle = mCircleRadius * (mDataCount - 1);
        float currentAngle;
        float x;
        float y;
        mPaint.setColor(Color.parseColor(mTextColor));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(mDataNameTextSize);
        mPaint.setStrokeWidth(5f);
        for (int i = 0; i < mDataCount; ++i) {
            currentAngle = mAngle * i;
            x = (float) (mCenterWidth + currentCircle * Math.cos(currentAngle));
            y = (float) (mCenterHeight + currentCircle * Math.sin(currentAngle));
            if (x < mCenterWidth) {
                x -= mPaint.measureText(data.get(i).getName()) + mDataNamePadding;
            } else {
                x += mDataNamePadding;
            }
            if (y > mCenterHeight) {
                y += mDataNamePadding;
            }
            canvas.drawText(data.get(i).getName(), x, y, mPaint);
        }
    }

    private void drawDataArea(Canvas canvas, List<SpiderData> data) {
        Path path = new Path();
        float x;
        float y;
        float maxCircleRadius = mCircleRadius * (mDataCount - 1);
        mPaint.setColor(Color.parseColor(mDataPointColor));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10f);
        for (int i = 0; i < mDataCount; ++i) {
            x = (float) (mCenterWidth + maxCircleRadius * data.get(i).getValue() * Math.cos(mAngle * i));
            y = (float) (mCenterHeight + maxCircleRadius * data.get(i).getValue() * Math.sin(mAngle * i));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawPoint(x, y, mPaint);
        }
        path.close();
        mPaint.setColor(Color.parseColor(mDataAreaColor));
        mPaint.setAlpha((int) (255 * mDataAreaAlpha));
        canvas.drawPath(path, mPaint);
    }

}
