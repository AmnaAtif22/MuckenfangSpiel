package com.example.myapplication;

import java.util.Random;

public class Mosquito {

    public float x, y;
    public float radius;
    private float vx, vy;

    public Mosquito(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;

        Random r = new Random();
        vx = r.nextFloat() * 6 - 3;
        vy = r.nextFloat() * 6 - 3;
    }

    public void move(int width, int height) {
        x += vx;
        y += vy;

        if (x < radius || x > width - radius) vx = -vx;
        if (y < radius || y > height - radius) vy = -vy;
    }

    public boolean isHit(float tx, float ty) {
        float dx = x - tx;
        float dy = y - ty;
        return dx * dx + dy * dy <= radius * radius;
    }
}