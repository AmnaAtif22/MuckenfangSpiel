package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    private Paint paint;
    private ArrayList<Mosquito> mosquitos;
    private Random random;

    private int score = 0;
    private ScoreListener scoreListener;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        mosquitos = new ArrayList<>();
        random = new Random();

        // Start-Mücken
        for (int i = 0; i < 5; i++) {
            mosquitos.add(createMosquito());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.rgb(180, 230, 250));

        paint.setColor(Color.BLACK);

        for (Mosquito m : mosquitos) {
            canvas.drawCircle(m.x, m.y, m.radius, paint);
            m.move(getWidth(), getHeight());
        }

        invalidate(); // neu zeichnen
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            for (int i = 0; i < mosquitos.size(); i++) {
                Mosquito m = mosquitos.get(i);
                if (m.isHit(x, y)) {
                    score += 10;
                    if (scoreListener != null) {
                        scoreListener.onScoreChanged(score);
                    }
                    mosquitos.set(i, createMosquito());
                    break;
                }
            }
        }
        return true;
    }

    private Mosquito createMosquito() {
        return new Mosquito(
                random.nextInt(800) + 100,
                random.nextInt(1200) + 200,
                40 + random.nextInt(20)
        );
    }

    public void setScoreListener(ScoreListener listener) {
        this.scoreListener = listener;
    }

    public interface ScoreListener {
        void onScoreChanged(int score);
    }
}
