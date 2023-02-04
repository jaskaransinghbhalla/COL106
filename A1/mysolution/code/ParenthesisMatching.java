package com.gradescope.assignment1;

import com.gradescope.assignment1.AbstractParenthesisMatching;
import com.gradescope.assignment1.DemoStack;

public class ParenthesisMatching extends AbstractParenthesisMatching {
    public Boolean match_parenthesis(String s) {
        DemoStack stemp = new DemoStack();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[') {
                stemp.push(ch);
            } else {
                if (stemp.is_empty()) {
                    return false;
                }
                if (stemp.top() == '(' && ch == ')' || stemp.top() == '[' && ch == ']'
                        || stemp.top() == '{' && ch == '}') {
                    stemp.pop();
                } else {
                    return false;
                }
            }

        }
        if (stemp.is_empty()) {
            return true;
        } else {
            return false;
        }
    }
}
