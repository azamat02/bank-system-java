package main.spring.controllers;

import jakarta.validation.Valid;
import main.spring.dao.UserDAO;
import main.spring.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserDAO userDAO;

    @Autowired
    public AuthController(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    @GetMapping("/signin")
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        return "/auth/sign_in";
    }

    @GetMapping("/signup")
    public String getRegPage(Model model){
        model.addAttribute("user", new User());
        return "/auth/sign_up";
    }

    @PostMapping("/signin")
    public String signIn(@ModelAttribute("user") User user, @PathVariable("password2") String password2){
        return "/main";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute("user") @Valid User user){
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        System.out.println(user.getLogin());
        System.out.println(user.getPhone());
        System.out.println(user.getPassword());
        return "/main/main";
    }
}
