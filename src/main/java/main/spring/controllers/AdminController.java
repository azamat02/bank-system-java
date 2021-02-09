package main.spring.controllers;

import com.google.gson.Gson;
import main.spring.dao.CardDAO;
import main.spring.dao.OperationDAO;
import main.spring.dao.UserDAO;
import main.spring.models.Card;
import main.spring.models.Operation;
import main.spring.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final CardDAO cardDAO;
    private final UserDAO userDAO;
    private final OperationDAO operationDAO;

    @Autowired
    public AdminController(CardDAO cardDAO, UserDAO userDAO, OperationDAO operationDAO)
    {
        this.cardDAO = cardDAO;
        this.userDAO = userDAO;
        this.operationDAO = operationDAO;
    }

    @GetMapping("/get_cards")
    public List<Card> getCardsList(){
        return cardDAO.getAllCardsList();
    }

    @GetMapping("/get_operations")
    public List<Operation> getOperationsList(){
        return  operationDAO.getAllOperationsList();
    }

}
