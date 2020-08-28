package ru.falin.RestaurantVotingSystem.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        return "redirect:dishes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dishes")
    public String dishes() {
        return "dishes";
    }

    @GetMapping("/restaurants")
    public String restaurants() {
        return "restaurants";
    }
}
