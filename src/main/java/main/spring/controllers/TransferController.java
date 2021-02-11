package main.spring.controllers;


import main.spring.dao.CardDAO;
import main.spring.dao.OperationDAO;
import main.spring.dao.UserDAO;
import main.spring.interfaces.TransferInterface;
import main.spring.models.Card;
import main.spring.models.Operation;
import main.spring.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("/transfer")
public class TransferController {
    private final CardDAO cardDAO;
    private final UserDAO userDAO;
    private final OperationDAO operationDAO;

    @Autowired
    public TransferController(CardDAO cardDAO, UserDAO userDAO, OperationDAO operationDAO)
    {
        this.cardDAO = cardDAO;
        this.userDAO = userDAO;
        this.operationDAO = operationDAO;
    }

    @PostMapping("/to_another_bank")
    public String transferToAnotherBank(Model model, Principal principal, @RequestParam("card_id") int card_id,
                                        @RequestParam("to_card") String to_card,
                                        @RequestParam("pay_sum1") int pay_sum1){
        int user_id = userDAO.findByLogin(principal.getName()).getId();
        Card card = cardDAO.findCardById(card_id);
        if (card.getBalance() < pay_sum1){
            model.addAttribute("message", "On selected card not enough money");
            return "/main/money_operations";
        } else {
//            Update card balance
            card.setBalance(card.getBalance()-pay_sum1);
//            Cut 1%
            TransferInterface transferInterface = (x)->{
                return x = x-x/100;
            };
            pay_sum1 = transferInterface.cut(pay_sum1);
            //Sending paysum1 to bank....
            //.....

            cardDAO.updateCardBalance(card);
//            Save operation
            String str = "Transfer from card "+card.card_type.toUpperCase(Locale.ROOT)+" **** "+card.getCard_number().substring(14,19);
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
            model.addAttribute("message", "Transfer done successfully!");
            return "/main/money_operations";
        }
    }
    @PostMapping("/to_this_bank")
    public String transferToThisBank(Model model, Principal principal, @RequestParam("card_id") int card_id,
                                        @RequestParam("to_card") String to_card,
                                        @RequestParam("pay_sum1") int pay_sum1){
        //GET CARD OF RECIEVER
        Card card_reciever = cardDAO.findCardByNumber(to_card);
        User user_reciever = userDAO.findById(card_reciever.getUser_id());

        //GET CARD OF SENDER
        Card card_sender = cardDAO.findCardById(card_id);
        User user_sender = userDAO.findByLogin(principal.getName());

        if (card_sender.getBalance() < pay_sum1){
            model.addAttribute("message", "On selected card not enough money");
            return "/main/money_operations";
        } else {
            //            Update card sender balance
            card_sender.setBalance(card_sender.getBalance()-pay_sum1);
            cardDAO.updateCardBalance(card_sender);
            if (pay_sum1 >= 100000){
                //Cut 1%
                TransferInterface transferInterface = (x)->{
                    return x = x-x/100;
                };
                pay_sum1 = transferInterface.cut(pay_sum1);
            }
//            Update card receiver balance
            card_reciever.setBalance(card_reciever.getBalance()+pay_sum1);
            cardDAO.updateCardBalance(card_reciever);

//            Save operation card sender operation
            String str = "Transfer from card "+card_sender.card_type.toUpperCase(Locale.ROOT)+" **** "+card_sender.getCard_number().substring(14,19);
            Date dt = new Date();
//            String st = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(dt);
            Operation operation = new Operation();
            operation.setBalance("-"+pay_sum1);
            operation.setOperation(str);
            operation.setDate(dt);
            operation.setUser_id(user_sender.getId());
            operation.setStatus("Done");
            operation.setFrom_card(" **** "+card_sender.getCard_number().substring(14,19));
            operationDAO.createOperation(operation);
//            Save operation card receiver operation
            String str2 = "Replenishment of the card "+card_reciever.card_type.toUpperCase(Locale.ROOT)+" **** "+card_reciever.getCard_number().substring(14,19);
//            String st = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(dt);
            Operation operation2 = new Operation();
            operation2.setBalance("+"+pay_sum1);
            operation2.setOperation(str2);
            operation2.setDate(dt);
            operation2.setUser_id(user_reciever.getId());
            operation2.setStatus("Done");
            operation2.setFrom_card(" **** "+card_reciever.getCard_number().substring(14,19));
            operationDAO.createOperation(operation2);
            model.addAttribute("message", "Transfer done successfully!");
            return "/main/money_operations";
        }
    }
    @PostMapping("/between_own_cards")
    public String transferBetweenOwnCards(Model model, Principal principal, @RequestParam("card_id_sender") int card_id_sender,
                                     @RequestParam("card_id_receiver") int card_id_receiver,
                                     @RequestParam("pay_sum1") int pay_sum1){
        //GET CARD OF RECIEVER
        Card card_reciever = cardDAO.findCardById(card_id_receiver);

        //GET CARD OF SENDER
        Card card_sender = cardDAO.findCardById(card_id_sender);
        User user_sender = userDAO.findByLogin(principal.getName());

        if (card_sender.getBalance() < pay_sum1){
            model.addAttribute("message", "On selected card not enough money");
            return "/main/money_operations";
        } else {
//            Update card sender balance
            card_sender.setBalance(card_sender.getBalance()-pay_sum1);
            cardDAO.updateCardBalance(card_sender);
//            Update card receiver balance
            card_reciever.setBalance(card_reciever.getBalance()+pay_sum1);
            cardDAO.updateCardBalance(card_reciever);

//            Save operation card sender operation
            String str = "Transfer from card "+card_sender.card_type.toUpperCase(Locale.ROOT)+" **** "+card_sender.getCard_number().substring(14,19);
            Date dt = new Date();
//            String st = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(dt);
            Operation operation = new Operation();
            operation.setBalance("-"+pay_sum1);
            operation.setOperation(str);
            operation.setDate(dt);
            operation.setUser_id(user_sender.getId());
            operation.setStatus("Done");
            operation.setFrom_card(" **** "+card_sender.getCard_number().substring(14,19));
            operationDAO.createOperation(operation);
//            Save operation card receiver operation
            String str2 = "Replenishment of the card "+card_reciever.card_type.toUpperCase(Locale.ROOT)+" **** "+card_reciever.getCard_number().substring(14,19);
//            String st = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(dt);
            Operation operation2 = new Operation();
            operation2.setBalance("+"+pay_sum1);
            operation2.setOperation(str2);
            operation2.setDate(dt);
            operation2.setUser_id(user_sender.getId());
            operation2.setStatus("Done");
            operation2.setFrom_card(" **** "+card_reciever.getCard_number().substring(14,19));
            operationDAO.createOperation(operation2);
            model.addAttribute("message", "Transfer done successfully!");
            return "/main/money_operations";
        }
    }
}
