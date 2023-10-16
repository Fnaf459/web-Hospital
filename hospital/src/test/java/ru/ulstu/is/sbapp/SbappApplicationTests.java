package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SbappApplicationTests {

    @Test
    public void testAddition() {
        int result = add(2, 3);
        assertEquals(5, result, "Ожидается, что 2 + 3 равно 5");
    }

    private int add(int a, int b) {
        return a + b;
    }
}
