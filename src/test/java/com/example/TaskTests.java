package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.models.Factorial;
import com.example.models.Fibonacci;
import com.example.models.Gcd;
import com.example.models.Task;

public class TaskTests {
    
    @Test
    public void testGcd(){
        Task GCD = new Gcd(12, 34);
        GCD.execute();
        assertEquals("The greatest common divisor of 12 and 34 is 2", GCD.getResults());
    }

    @Test
    public void testFactorial(){
        Task factorial = new Factorial(11);
        factorial.execute();
        assertEquals("The factorial of 11 = 39916800", factorial.getResults());
    }

    @Test
    public void testFibonacci(){
        Task fibonacci = new Fibonacci(11);
        fibonacci.execute();
        assertEquals("0 + 1 + 1 + 2 + 3 + 5 + 8 + 13 + 21 + 34 + 55 +", fibonacci.getResults());
    }
}
