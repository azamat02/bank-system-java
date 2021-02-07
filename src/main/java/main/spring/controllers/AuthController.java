package main.spring.controllers;

import jakarta.validation.Valid;
import main.spring.dao.UserDAO;
import main.spring.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserDAO userDAO;

    PasswordEncoder passwordEncoder;
    @Autowired
    public void PasswordEncoder(PasswordEncoder passwordEncoder)
    { this.passwordEncoder = passwordEncoder;}

    @Autowired
    public AuthController(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    @GetMapping("/signin")
    public String getLoginPage(){
        return "/auth/sign_in";
    }

    @GetMapping("/signup")
    public String getRegPage(Model model){
        model.addAttribute("user", new User());
        return "/auth/sign_up";
    }

//    Registration
    @PostMapping("/signup")
    public String signUp(Model model, @RequestParam("password2") String password2, @ModelAttribute("user") User user){
        String error_message = "";
        if (userDAO.findByLogin(user.getLogin()) != null){
            error_message = "Entered login is exist!";
            model.addAttribute("message", error_message);
            return "/auth/sign_up";
        }
        if (!user.getPassword().equals(password2)){
            error_message = "Entered passwords are not same!";
            model.addAttribute("message", error_message);
            return "/auth/sign_up";
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDAO.createUser(user);
            return "redirect: /auth/signin";
        }
    }

    //Change password
    @PostMapping("/changepass")
    public String changePass(Principal principal, Model model, @RequestParam("password") String password, @RequestParam("password2") String password2){
        User us = userDAO.findByLogin(principal.getName());
        password = passwordEncoder.encode(password);
        us.setPassword(password);
        userDAO.changePassOfUser(us);
        model.addAttribute("message", "Password successfully changed!");
        return "/profile/profile";
    }
}
