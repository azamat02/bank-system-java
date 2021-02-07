package main.spring.controllers;

import main.spring.dao.CardDAO;
import main.spring.dao.OperationDAO;
import main.spring.dao.UserDAO;
import main.spring.models.Card;
import main.spring.models.Operation;
import main.spring.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final CardDAO cardDAO;
    private final UserDAO userDAO;
    private final OperationDAO operationDAO;

    PasswordEncoder passwordEncoder;
    @Autowired
    public void PasswordEncoder(PasswordEncoder passwordEncoder)
    { this.passwordEncoder = passwordEncoder;}

    @Autowired
    public ProfileController(CardDAO cardDAO, UserDAO userDAO, OperationDAO operationDAO)
    {
        this.cardDAO = cardDAO;
        this.userDAO = userDAO;
        this.operationDAO = operationDAO;
    }

    @GetMapping("")
    public String profilePage(Model model, Principal principal){
        User us = userDAO.findByLogin(principal.getName());
        model.addAttribute("user", us);
        return "/profile/profile";
    }

    @GetMapping("/cards")
    public String cardsPage(Model model, Principal principal){
        List<Card> cardList = cardDAO.findByUserId(userDAO.findByLogin(principal.getName()).getId());
        model.addAttribute("card_list", cardList);
        return "/profile/cards";

    }

    @GetMapping("/operation_history")
    public String operationHistoryPage(Principal principal, Model model){
        int user_id = userDAO.findByLogin(principal.getName()).getId();
        List<Operation> operations = operationDAO.findOperationsByUserId(user_id);
        model.addAttribute("opers", operations);
        return "/profile/operation_history";
    }

    @GetMapping("/card/create_card")
    public String createCardPage(){
        return "/profile/create_card";
    }

    @PostMapping("/card/create_card")
    public String createCard(Principal principal, @RequestParam("card_holder_name") String name,
                             @RequestParam("card_number") String number,
                             @RequestParam("card_expire_date") String expire,
                             @RequestParam("card_cvv") String cvv,
                             @RequestParam("card_type") String type){
        int user_id = userDAO.findByLogin(principal.getName()).getId();
        Card card = new Card();
        card.setCard_number(number);
        card.setCard_type(type);
        card.setCvv(Integer.valueOf(cvv));
        card.setBalance(0);
        card.setExpired_date(expire);
        card.setUser_id(user_id);

        cardDAO.createCard(card);

        return "redirect:/profile/cards";
    }
}
