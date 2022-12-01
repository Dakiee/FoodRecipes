package com.example.foodrecipes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void testValidateLogin() {
        LoginController temp = new LoginController();
        temp.validateLogin("dakie","Dakie123");
        assertEquals(null, LoginController.user);
    }
}