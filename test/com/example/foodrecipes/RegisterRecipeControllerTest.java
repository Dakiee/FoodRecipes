package com.example.foodrecipes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRecipeControllerTest {

    @Test
    void testIsPasswordValid() {
        RegisterController test = new RegisterController();
        Boolean temp = test.isPasswordValid("Password123");
        assertEquals(false, temp);
    }
}