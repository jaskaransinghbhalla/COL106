package com.gradescope.assignment1;

import java.util.EmptyStackException;

import com.gradescope.assignment1.AbstractDemoStack;

public class DemoStack extends AbstractDemoStack {
    Character[] base;
    private int size;
    private int N;

    public DemoStack() {
        super();
        base = new Character[10];
        size = 0;
        N = 10;
    }

    public void push(Character i) {
        if (size == base.length) {
            Character[] temp = base;
            N = 2 * N;
            base = new Character[N];
            for (int j = 0; j < size; j++) {
                base[j] = temp[j];
            }
        }
        base[size] = i;
        size = size + 1;
    }

    public Character pop() throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            Character value = base[size - 1];
            size = size - 1;
            if (size < N / 4 && N > 40) {
                Character[] temp = base;
                N = N/2;
                base = new Character[N];
                for (int i = 0; i < size; i++) {
                    base[i] = temp[i];
                }
            }
            return value;
        }
    }

    public Boolean is_empty() {
        if (size != 0) {
            return false;
        }
        return true;
    }

    public Integer size() {
        return size;
    }

    public Character top() throws EmptyStackException {
        return base[size - 1];
    }

    public Character[] return_base_array() {
        /*
         * To be filled in the by the student
         * Return: Return reference to the base array storing the elements of stack
         */
        return base;
    }
}
