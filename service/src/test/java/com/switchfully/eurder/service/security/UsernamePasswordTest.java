package com.switchfully.eurder.service.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UsernamePasswordTest {
    @Test
    void creatingUsernamePassword() {
        String username = "username";
        String password = "password";
        UsernamePassword usernamePassword = new UsernamePassword(username, password);

        assertThat(usernamePassword).isNotNull();
        assertThat(usernamePassword.getUsername()).isEqualTo(username);
        assertThat(usernamePassword.getPassword()).isEqualTo(password);
    }

}