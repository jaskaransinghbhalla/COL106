package com.gradescope.assignment1;

import com.gradescope.assignment1.AbstractMethodOverloading;
import java.lang.Math;

public class MethodOverloading extends AbstractMethodOverloading {
    public double calculate(int a) {
        return a * a;
    }

    public double calculate(int a, int b) {
        return a * b;
    }

    public double calculate(int a, int b, int c) {
        double s = (a + b + c) / 2;
        double temp = s * (s - a) * (s - b) * (s - c);
        double area = Math.sqrt(temp);
        return area;
    }
}
