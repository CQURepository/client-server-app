package com.example.models;

import java.io.Serializable;


public class Fibonacci implements Task, Serializable {

    private int number;
    private String result;


    public Fibonacci(int num) {
        this.number = num;
        result = "";
    }

    @Override
    public String getResults() {
        return this.result.trim();
    }

    @Override
    public void execute() {
        int num1 = 0, num2 = 1;
        for (int i = 0; i < number; ++i) {
            this.result += num1 + " + ";
            int sum = num1 + num2;
            num1 = num2;
            num2 = sum;
        }
    }

}
