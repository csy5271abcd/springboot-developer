package me.shinsunyoung.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "oauthLogin"; // oauthLogin.html 반환
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup"; // signup.html 반환
    }
}
