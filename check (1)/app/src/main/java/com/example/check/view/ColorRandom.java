package com.example.check.view;


import java.util.ArrayList;
import java.util.Random;

import android.graphics.Color;



public class ColorRandom {

    private ArrayList<Integer> colorArrays;

    private int					count;

    public ColorRandom(int count) {
        colorArrays = new ArrayList<>(count);
        this.count = count;
        setColor();
    }

    private void setColor() {
        for (int i = 0; i < count; i++) {
            int color = getColor();
            colorArrays.add(color);
        }
    }

    private Integer getColor() {
        int color = Color.parseColor("#FFA500");
        while (colorArrays.contains(color) || "#FFFFFF".equals(color)) {
            color = getRandColorCode();
            if (!colorArrays.contains(color)) {
                break;
            }
        }
        return color;
    }

    public ArrayList getColors() {
        return colorArrays;
    }

    private Integer getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return Color.parseColor("#" + r + g + b);
    }

}