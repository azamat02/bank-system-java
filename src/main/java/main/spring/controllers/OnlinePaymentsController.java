package main.spring.controllers;

import main.spring.dao.CardDAO;
import main.spring.dao.OperationDAO;
import main.spring.dao.UserDAO;
import main.spring.models.Card;
import main.spring.models.Operation;
import main.spring.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.Locale;


@Controller
@RequestMapping("/orgpayments")
public class OnlinePaymentsController {
    private final CardDAO cardDAO;
    private final UserDAO userDAO;
    private final OperationDAO operationDAO;

    @Autowired
    public OnlinePaymentsController(CardDAO cardDAO, UserDAO userDAO, OperationDAO operationDAO)
    {
        this.cardDAO = cardDAO;
        this.userDAO = userDAO;
        this.operationDAO = operationDAO;
    }

    @PostMapping("/to_house_services")
    public String transferToHouseServices(Model model, Principal principal, @RequestParam("card_id") int card_id,
                                        @RequestParam("to_service") String to_service,
                                        @RequestParam("pay_sum1") int pay_sum1){
        int user_id = userDAO.findByLogin(principal.getName()).getId();
        Card card = cardDAO.findCardById(card_id);
        if (card.getBalance() < pay_sum1){
            model.addAttribute("message", "On selected card not enough money");
            return "/main/money_operations";
        } else {
//            Update card balance
            card.setBalance(card.getBalance()-pay_sum1);
            cardDAO.updateCardBalance(card);
//            Save operation
            String str = "Online payment "+to_service+" from card "+card.card_type.toUpperCase(Locale.ROOT)+" **** "+card.getCard_number().substring(14,19);
            Date dt = new Date();
//            String st = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(dt);
            Operation operation = new Operation();
            operation.setBalance("-"+pay_sum1);
            operation.setOperation(str);
            operation.setDate(dt);
            operation.setUser_id(user_id);
            operation.setStatus("Done");
            operation.setFrom_card(" **** "+card.getCard_number().substring(14,19));
            operationDAO.createOperation(operation);
            model.addAttribute("message", "Online payment done successfully!");
            return "/main/online_payments";
        }
    }
    @PostMapping("/to_mobile_service")
    public String transferToMobileServices(Model model, Principal principal, @RequestParam("card_id") int card_id,
                                        @RequestParam("phone_number") String phone_number,
                                        @RequestParam("pay_sum1") int pay_sum1){
        int user_id = userDAO.findByLogin(principal.getName()).getId();
        Card card = cardDAO.findCardById(card_id);
        if (card.getBalance() < pay_sum1){
            model.addAttribute("message", "On selected card not enough money");
            return "/main/money_operations";
        } else {
//            Update card balance
            card.setBalance(card.getBalance()-pay_sum1);
            cardDAO.updateCardBalance(card);
//            Save operation
            String str = "Online payment for mobile service on phone:"+phone_number+" from card "+card.card_type.toUpperCase(Locale.ROOT)+" **** "+card.getCard_number().substring(14,19);
            Date dt = new Date();
//            String st = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(dt);
            Operation operation = new Operation();
            operation.setBalance("-"+pay_sum1);
            operation.setOperation(str);
            operation.setDate(dt);
            operation.setUser_id(user_id);
            operation.setStatus("Done");
            operation.setFrom_card(" **** "+card.getCard_number().substring(14,19));
            operationDAO.createOperation(operation);
            model.addAttribute("message", "Online payment done successfully!");
            return "/main/online_payments";
        }
    }

    @PostMapping("/to_charity")
    public String transferToCharity(Model model, Principal principal, @RequestParam("card_id") int card_id,
                                           @RequestParam("organization") String org,
                                           @RequestParam("pay_sum1") int pay_sum1){
        int user_id = userDAO.findByLogin(principal.getName()).getId();
        Card card = cardDAO.findCardById(card_id);
        if (card.getBalance() < pay_sum1){
            model.addAttribute("message", "On selected card not enough money");
            return "/main/money_operations";
        } else {
//            Update card balance
            card.setBalance(card.getBalance()-pay_sum1);
            cardDAO.updateCardBalance(card);
//            Save operation
            String str = "Charity for "+org+" from card "+card.card_type.toUpperCase(Locale.ROOT)+" **** "+card.getCard_number().substring(14,19);
            Date dt = new Date();
//            String st = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(dt);
            Operation operation = new Operation();
            operation.setBalance("-"+pay_sum1);
            operation.setOperation(str);
            operation.setDate(dt);
            operation.setUser_id(user_id);
            operation.setStatus("Done");
            operation.setFrom_card(" **** "+card.getCard_number().substring(14,19));
            operationDAO.createOperation(operation);
            model.addAttribute("message", "Online payment done successfully!");
            return "/main/online_payments";
        }
    }
}
