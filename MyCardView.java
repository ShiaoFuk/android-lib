package com.example.testsoundrecord;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MyCardView extends CardView {
    Paint paint;
    LinkedList<Float> lineQueue;
    public MyCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        lineQueue = new LinkedList<>();
        for (int i = 0; i < 11; ++i) lineQueue.add(0.0F);  // 初始化大小为6的queue
    }

    // 添加一条线段并重绘
    public void addWaveLine(float length) {
        lineQueue.add(length);
        lineQueue.removeFirst();
        this.invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStrokeCap(Paint.Cap.ROUND);
        int strokeWidth = 20;
        paint.setStrokeWidth(strokeWidth);
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() / 2;
        int interval = 40;
        int size = lineQueue.size();
        // 绘制以前的时间线
        for (int i = 0; i < size; ++i) {
            float len = lineQueue.get(i);
            int modulus = i - (size - 1);  // 确保队列最后一个在中间
            float startX = x;
            startX += interval * modulus;
            float startY = y - len / 2;
            float endY = y + len / 2;
            canvas.drawLine(startX, startY, startX, endY, paint);
        }
        // 绘制未来时间线，全部为0
        for (int i = 0; i < size-1; ++i) {
            int len = 50;
            int modulus = i + 1;  // 确保队列最后一个在中间
            float startX = x + interval * modulus;
            float startY = y - len / 2;
            float endY = y + len / 2;
            canvas.drawLine(startX, startY, startX, endY, paint);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
    }
}
