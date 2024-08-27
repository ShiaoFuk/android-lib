package com.example.testsoundrecord;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PieChart extends View {

    Paint paint;
    ArrayList<String> keyList;
    ArrayList<Float> valueList;  // 是个百分比
    ArrayList<Float> sweepAngleList;
    float radius;
    private static final String title = "病情等级";  // 居中显示的文字，在content上方，字体更小
    private int level;  // 居中显示的文字，更大的字体
    List<int[]> colorList;  // 三元组，这个颜色列表的颜色绘制,超出列表容量则随机颜色
    public PieChart(Context context) {
        super(context);
        init();
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void addItems(List<String> keyList1, List<Float> valueList1) {
        this.keyList.addAll(keyList1);
        this.valueList.addAll(valueList1);
        updateSweepAngle();
    }

    public void addItem(String key, float value) {
        keyList.add(key);
        valueList.add(value);
        updateSweepAngle();
    }

    private void updateSweepAngle() {
        int size = valueList.size();
        this.sweepAngleList = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            this.sweepAngleList.add(this.valueList.get(i) * 360f);
        }
        setLevel();  // 设置概率最大为病情等级
    }

    private void setLevel() {
        int maxLevel = -1;
        float maxVal = -1;
        for (int i = 0; i < sweepAngleList.size(); ++i) {
            if (i == 0) {
                maxLevel = i + 1;
                maxVal = sweepAngleList.get(i);
            } else {
                if (sweepAngleList.get(i) > maxVal) {
                    maxLevel = i + 1;
                    maxVal = sweepAngleList.get(i);
                }
            }
        }
        this.level = maxLevel;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
    private void init() {
        paint = new Paint();
        keyList = new ArrayList<>();
        valueList = new ArrayList<>();
        sweepAngleList = new ArrayList<>();
        radius = 250f;
        initColor();
    }

    private void initColor() {
        colorList = new ArrayList<>();
        colorList.add(new int[]{170, 218, 255});
        colorList.add(new int[]{119, 196, 255});
        colorList.add(new int[]{66, 173, 255});
        colorList.add(new int[]{255, 153, 43});
    }
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        float centerX = width / 2;
        float centerY = height / 2;
        drawArc(canvas, centerX, centerY);
        drawTitle(canvas, centerX, centerY);
        drawLegend(canvas, width, centerY);
    }

    void drawTitle(Canvas canvas, float cx, float cy) {
        String level = "";
        switch (this.level) {
            case 1:
                level = "一级";
                break;
            case 2:
                level = "二级";
                break;
            case 3:
                level = "三级";
                break;
            case 4:
                level = "四级";
                break;
            case 5:
                level = "五级";
                break;
            case 6:
                level = "六级";
                break;
            case 7:
                level = "七级";
                break;
            case 8:
                level = "八级";
                break;
            case 9:
                level = "九级";
                break;
            default:
        }
        float length = radius / 3f;
        paint.setTextSize(100f);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float textHeight = fontMetrics.bottom - fontMetrics.top;
        while (textHeight > length) {
            paint.setTextSize(paint.getTextSize() - 1);
            fontMetrics = paint.getFontMetrics();
            textHeight = fontMetrics.bottom - fontMetrics.top;
        }
        int[] color = colorList.get(this.level - 1);
        paint.setColor(Color.rgb(color[0], color[1], color[2]));
        paint.setStyle(Paint.Style.FILL);

        float textWidth = paint.measureText(level);
        float x = cx - textWidth / 2f;
//        float y = cy - textHeight / 2f;
        float y = cy + textHeight / 3f;
        canvas.drawText(level, x, y, paint);
        paint.setColor(Color.BLACK);
        float levelTextWidth = textWidth;
        paint.setTextSize(100f);
        textWidth = paint.measureText(title);
        while (textWidth > levelTextWidth) {
            paint.setTextSize(paint.getTextSize() - 1);
            textWidth = paint.measureText(title);
        }
        fontMetrics = paint.getFontMetrics();
        textHeight = fontMetrics.bottom - fontMetrics.top;
        x = cx - textWidth / 2f;
        y = y - textHeight * 1.5f;
        canvas.drawText(title, x, y, paint);
    }

    void drawArc(Canvas canvas, float cx, float cy) {
        float left = cx - radius;
        float top = cy - radius;
        float right = cx + radius;
        float bottom = cy + radius;
        // 绘制圆弧
        RectF rectF = new RectF(left, top, right, bottom);
        paint.setStrokeWidth(radius / 3f);
        paint.setStyle(Paint.Style.STROKE);
        float totalAngle = -90f;
        for (int i = 0; i < sweepAngleList.size(); ++i) {
            if (i < colorList.size()) {
                // 设置颜色
                int[] color = colorList.get(i);
                paint.setColor(Color.rgb(color[0], color[1], color[2]));
            } else {
                int[] color = new int[]{(int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)};
                colorList.add(color);
                paint.setColor(Color.rgb(color[0], color[1], color[2]));
            }
            canvas.drawArc(rectF, totalAngle, sweepAngleList.get(i), false, paint);
            totalAngle += sweepAngleList.get(i);
        }
    }
    void drawLegend(Canvas canvas, float width, float cy) {
        float length = radius / 5f;
        float margin = (length / 3) * 2f;
        int size = keyList.size();
        // 计算开始绘图的初始高度
        float starty = 0;
        if (size % 2 == 0) {
            // 偶数
            int num = size / 2;
            starty = cy - num * (length + margin) + margin / 2;
        } else {
            int num = (size - 1) / 2;
            starty = cy - num * (length + margin) - length / 2;
        }
        // 计算文字大小
        paint.setTextSize(100f);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float textHeight = fontMetrics.bottom - fontMetrics.top;
        while (textHeight > length) {
            paint.setTextSize(paint.getTextSize() - 1);
            fontMetrics = paint.getFontMetrics();
            textHeight = fontMetrics.bottom - fontMetrics.top;
        }
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < size; ++i) {
            if (i < colorList.size()) {
                // 设置颜色
                int[] color = colorList.get(i);
                paint.setColor(Color.rgb(color[0], color[1], color[2]));
            } else {
                int[] color = new int[]{(int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)};
                colorList.add(color);
                paint.setColor(Color.rgb(color[0], color[1], color[2]));
            }
            canvas.drawRect(margin, starty, margin + length, starty + length, paint);
            float textY = (starty + (length / 3) * 2);
            float textX = margin + length + margin / 2;
            paint.setColor(Color.BLACK);
            canvas.drawText(keyList.get(i), textX, textY, paint);
            starty += (length + margin);
        }
    }
}
