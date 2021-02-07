package main.spring.controllers;

import main.spring.dao.CardDAO;
import main.spring.dao.OperationDAO;
import main.spring.dao.UserDAO;
import main.spring.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class MainController {
    private final CardDAO cardDAO;
    private final UserDAO userDAO;
    private final OperationDAO operationDAO;

    @Autowired
    public MainController(CardDAO cardDAO, UserDAO userDAO, OperationDAO operationDAO)
    {
        this.cardDAO = cardDAO;
        this.userDAO = userDAO;
        this.operationDAO = operationDAO;
    }

    @GetMapping("/")
    public String mainPage(){
        return "/main/main";
    }

    @GetMapping("/online_payments")
    public String onlinePaymentsPage(Principal principal, Model model){
        //        GET CARDS
        List<Card> user_cards = cardDAO.findByUserId(userDAO.findByLogin(principal.getName()).getId());

        for (Card card: user_cards) {
            String str = card.getCard_type().toUpperCase()+" **** "+card.getCard_number().substring(14,19);
            card.setCard_number(str);
        }

        model.addAttribute("user_cards", user_cards);
        model.addAttribute("message", "");

        return "/main/online_payments";
    }

    @GetMapping("/money_operations")
    public String moneyOperationsPage(Principal principal, Model model){
//        GET CARDS
        List<Card> user_cards = cardDAO.findByUserId(userDAO.findByLogin(principal.getName()).getId());

        for (Card card: user_cards) {
            String str = card.getCard_type().toUpperCase()+" **** "+card.getCard_number().substring(14,19);
            card.setCard_number(str);
        }

        model.addAttribute("user_cards", user_cards);
        model.addAttribute("message", "");

        return "/main/money_operations";
    }

}
