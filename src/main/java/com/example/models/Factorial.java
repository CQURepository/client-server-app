package com.example.models;

import java.io.Serializable;


public class Factorial implements Task, Serializable {

    private int number;
    private String result;

    public Factorial(int number) {
        this.number = number;
    }

    @Override
    public String getResults() {
        return "The factorial of " + number + " = " + this.result;
    }

    @Override
    public void execute() {
        int sum = 1;
        for (int i = 0; i <= number; ++i) {
            sum *= (i > 0) ? i : 1;
            this.result = String
                    .valueOf(sum < Integer.MAX_VALUE && sum > 0 ? sum : "Answer exceeds maximum value");
        }
    }

}
