package com.example.axiang.warmstomach.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 设置循环变色的TextView
 * Created by a2389 on 2017/12/5.
 */

public class CyclicalTextView extends AppCompatTextView {

    private Paint mPaint;

    private int mViewWidth = 0;

    private Matrix mGradientMatrix;

    private LinearGradient mLinearGradient;

    private boolean mAnimating = true;

    private int mTranslate = 0;

    private int mDelta = 15;

    public CyclicalTextView(Context context) {
        super(context, null);
    }

    public CyclicalTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                String text = getText().toString();
                int size;
                if (text.length() > 0) {
                    size = mViewWidth * 2 / text.length();
                } else {
                    size = mViewWidth;
                }
                mLinearGradient = new LinearGradient(-size, 0, 0, 0,
                        new int[] {0x33ffffff, 0xffffffff, 0x33ffffff},
                        new float[] {0, 0.5f, 1},
                        //边缘融合
                        Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int length = Math.max(length(), 1);
        if (mAnimating && mGradientMatrix != null) {
            float textWidth = getPaint().measureText(getText().toString());
            mTranslate += mDelta;
            if (mTranslate > textWidth + 1 || mTranslate < 1) {
                mDelta = -mDelta;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(30);
        }
    }
}