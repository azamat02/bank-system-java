package main.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(){
        return "/main/main";
    }

    @GetMapping("/online_payments")
    public String onlinePaymentsPage(){
        return "/main/online_payments";
    }

    @GetMapping("/money_operations")
    public String moneyOperationsPage(){
        return "/main/money_operations";
    }

}
