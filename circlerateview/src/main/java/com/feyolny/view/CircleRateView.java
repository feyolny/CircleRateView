package com.feyolny.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by feyolny on 2019/3/13.
 * Describtion:带动画的百分比圆形控件
 */

public class CircleRateView extends View {

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 控件的宽高
     */
    private int viewHeight, viewWidth;

    /**
     * 内圆和外圆的画笔
     */
    private Paint outCirclePaint, innerCirclePaint;

    /**
     * 内圆和外圆的颜色
     */
    private int outCircleColor, innerCircleColor;

    /**
     * 内圆和外圆的画笔宽度
     */
    private int outCircleStrokeWidth, innerCircleStrokeWidth;

    /**
     * 比率数字画笔和其他文字的画笔
     */
    private Paint ratePaint, expressPaint, percentPaint;

    /**
     * 比率数字画笔和其他文字的画笔的颜色
     */
    private int rateColor, expressColor, percentColor;

    /**
     * 比率数字和其他文字的大小
     */
    private int rateSize, expressSize, percentSize;

    /**
     * 比率数字  默认45
     */
    private String rateText;

    /**
     * 比率代表的含义  比如 正确率、签到率等等
     */
    private String expressText;

    /**
     * 比率对应的弧度
     */
    private float sweep;

    /**
     * 动画进行的时间
     */
    private int duration;


    public CircleRateView(Context context) {
        super(context);
        initView(context, null, -1);
    }

    public CircleRateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, -1);
    }

    public CircleRateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

        mContext = context;

        //通过读取attr文件获取属性值，无配置即默认值
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleRateView);
        outCircleColor = typedArray.getColor(R.styleable.CircleRateView_outCircleColor, Color.BLACK);
        innerCircleColor = typedArray.getColor(R.styleable.CircleRateView_innerCircleColor, Color.BLACK);
        rateColor = typedArray.getColor(R.styleable.CircleRateView_rateColor, Color.BLACK);
        expressColor = typedArray.getColor(R.styleable.CircleRateView_expressColor, Color.BLACK);
        percentColor = typedArray.getColor(R.styleable.CircleRateView_percentColor, Color.BLACK);
        rateSize = typedArray.getDimensionPixelSize(R.styleable.CircleRateView_rateSize, dip2px(50));
        expressSize = typedArray.getDimensionPixelSize(R.styleable.CircleRateView_expressSize, dip2px(16));
        percentSize = typedArray.getDimensionPixelSize(R.styleable.CircleRateView_percentSize, dip2px(16));
        outCircleStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.CircleRateView_outCircleStrokeWidth, dip2px(0.5f));
        innerCircleStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.CircleRateView_innerCircleStrokeWidth, dip2px(0.5f));
        rateText = typedArray.getString(R.styleable.CircleRateView_rateText);
        if (TextUtils.isEmpty(rateText)) {
            rateText = "45";
        }
        expressText = typedArray.getString(R.styleable.CircleRateView_expressText);
        if (TextUtils.isEmpty(expressText)) {
            expressText = "正确率";
        }
        duration = typedArray.getInt(R.styleable.CircleRateView_duration, 2000);

        //初始化相关画笔
        outCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outCirclePaint.setStrokeWidth(dip2px(outCircleStrokeWidth));
        outCirclePaint.setStyle(Paint.Style.STROKE);
        outCirclePaint.setColor(outCircleColor);

        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setStrokeWidth(dip2px(innerCircleStrokeWidth));
        innerCirclePaint.setStyle(Paint.Style.STROKE);
        innerCirclePaint.setColor(innerCircleColor);

        ratePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ratePaint.setStrokeWidth(dip2px(1));
        ratePaint.setStyle(Paint.Style.FILL);
        ratePaint.setTextSize(rateSize);
        ratePaint.setTextAlign(Paint.Align.CENTER);
        ratePaint.setColor(rateColor);

        expressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        expressPaint.setStrokeWidth(dip2px(1));
        expressPaint.setStyle(Paint.Style.FILL);
        expressPaint.setTextSize(expressSize);
        expressPaint.setTextAlign(Paint.Align.CENTER);
        expressPaint.setColor(expressColor);

        percentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        percentPaint.setStrokeWidth(dip2px(1));
        percentPaint.setStyle(Paint.Style.FILL);
        percentPaint.setTextSize(percentSize);
        percentPaint.setTextAlign(Paint.Align.LEFT);
        percentPaint.setColor(percentColor);

        //计算比率所对应的弧度
        sweep = Float.parseFloat(rateText) / 100 * 360;

        //回收typedArray对象，避免OOM
        typedArray.recycle();

        //开启动画
        startAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthValue = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightValue = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            viewWidth = widthValue;
        } else {
            viewWidth = dip2px(162);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            viewHeight = heightValue;
        } else {
            viewHeight = dip2px(162);
        }

        if (viewWidth <= viewHeight) {
            viewHeight = viewWidth;
        } else {
            viewWidth = viewHeight;
        }

        setMeasuredDimension(viewWidth, viewHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外圆
        canvas.drawCircle(viewWidth / 2, viewHeight / 2, viewWidth / 2 - outCircleStrokeWidth - dip2px(1), outCirclePaint);
        //画内圆
        RectF rectF = new RectF(outCircleStrokeWidth + innerCircleStrokeWidth + dip2px(8), outCircleStrokeWidth + innerCircleStrokeWidth + dip2px(8), viewWidth - dip2px(8) - outCircleStrokeWidth - innerCircleStrokeWidth, viewHeight - dip2px(8) - outCircleStrokeWidth - innerCircleStrokeWidth);
        canvas.drawArc(rectF, 90, sweep, false, innerCirclePaint);

        //画文字
        drawRateAndOther(canvas);

    }


    /**
     * 画比率数字和其他的文字
     *
     * @param canvas
     */
    private void drawRateAndOther(Canvas canvas) {

        //获得比率数字的FontMetricsInt
        Paint.FontMetricsInt rateFont = ratePaint.getFontMetricsInt();
        ratePaint.setTextAlign(Paint.Align.CENTER);
        int rateX = viewWidth / 2;
        int rateY = viewHeight / 2 + (rateFont.descent / 2 - rateFont.ascent / 2 - rateFont.descent);
        canvas.drawText(rateText, rateX, rateY, ratePaint);

        //获取表达意思文字的FontMetricsInt
        Paint.FontMetricsInt textFout = expressPaint.getFontMetricsInt();
        expressPaint.setTextAlign(Paint.Align.CENTER);
        int expressX = viewWidth / 2;
        int expressY = (int) (viewHeight * 0.26 + (textFout.descent / 2 - textFout.ascent / 2 - textFout.descent));
        canvas.drawText(expressText, expressX, expressY, expressPaint);

        //根据比率文字的宽度来计算百分比的x坐标，y坐标则和比率保持一致
        percentPaint.setTextAlign(Paint.Align.LEFT);
        float rateWidth = ratePaint.measureText(rateText);
        int percentX = (int) (rateWidth / 2 + viewWidth / 2 + dip2px(11));
        int percentY = viewHeight / 2 + (rateFont.descent / 2 - rateFont.ascent / 2 - rateFont.descent);
        canvas.drawText("%", percentX, percentY, percentPaint);

    }

    /**
     * dip 转 px
     *
     * @param dipValue
     * @return
     */
    public int dip2px(float dipValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }


    /**
     * 开启动画
     */
    private void startAnimation() {
        ValueAnimator valueAnimator = new ValueAnimator().ofFloat(0, sweep);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweep = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 设置更新比率
     *
     * @param rateText
     */
    public void setRateText(String rateText) {
        if (TextUtils.isEmpty(rateText)) {
            rateText = "0";
        }
        this.rateText = rateText;
        sweep = Float.parseFloat(rateText) / 100 * 360;
        startAnimation();
    }
}
