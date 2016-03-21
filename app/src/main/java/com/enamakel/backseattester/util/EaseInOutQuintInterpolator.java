package com.enamakel.backseattester.util;


import android.view.animation.Interpolator;


public class EaseInOutQuintInterpolator implements Interpolator {
    double power = 1;


    public EaseInOutQuintInterpolator(double power) {
        this.power = power;
    }


    public float power(float x) {
        return (float) Math.pow(x, power);
    }


    public float getInterpolation(float t) {
        float x;
        if (t < 0.5f) {
            x = t * 2.0f;
            return 0.5f * power(x);
        }
        x = (t - 0.5f) * 2 - 1;
        return 0.5f * power(x) + 1;
    }
}
