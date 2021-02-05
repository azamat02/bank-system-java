package main.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/signin")
    public String getLoginPage(){
        return "/auth/sign_in";
    }

    @GetMapping("/signup")
    public String getRegPage(){
        return "/auth/sign_up";
    }
}
