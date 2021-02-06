package main.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping("")
    public String profilePage(){
        return "/profile/profile";
    }

    @GetMapping("/cards")
    public String cardsPage(){
        return "/profile/cards";
    }

    @GetMapping("/operation_history")
    public String operationHistoryPage(){
        return "/profile/operation_history";
    }
}
