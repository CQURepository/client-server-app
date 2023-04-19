package com.example.models;

import java.io.Serializable;


public class Gcd implements Task, Serializable {

    private int number1;
    private int number2;
    private String result;

    public Gcd(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    @Override
    public String getResults() {
        return String.format("The greatest common divisor of %d and %d is %s", number1, number2, result);
    }

    @Override
    public void execute() {
        for (int i = 1; i <= number1 && i <= number2; ++i) {
            this.result = (number1 % i == 0 && number2 % i == 0) ? "" + i : result;
        }
    }

}
