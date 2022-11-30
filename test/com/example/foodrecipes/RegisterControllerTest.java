package com.example.foodrecipes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterControllerTest {

    @Test
    void testIsPasswordValid() {
        RegisterController test = new RegisterController();
        Boolean temp = test.isPasswordValid("Suganii us");
        assertEquals(false, temp);
    }
}