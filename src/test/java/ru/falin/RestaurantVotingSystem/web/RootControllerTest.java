package ru.falin.RestaurantVotingSystem.web;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.falin.RestaurantVotingSystem.TestUtil.userAuth;
import static ru.falin.RestaurantVotingSystem.UserTestData.ADMIN;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void getUsers()throws Exception {
        perform(get("/users")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }
}