package main.spring.controllers;

import main.spring.dao.CardDAO;
import main.spring.dao.OperationDAO;
import main.spring.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@RestController
@RequestMapping("/payment")
public class RestAPI {
    private final CardDAO cardDAO;
    private final UserDAO userDAO;
    private final OperationDAO operationDAO;

    @Autowired
    public RestAPI(CardDAO cardDAO, UserDAO userDAO, OperationDAO operationDAO)
    {
        this.cardDAO = cardDAO;
        this.userDAO = userDAO;
        this.operationDAO = operationDAO;
    }


    @GetMapping("/card_exist")
    public void cardExist(HttpServletResponse response, @RequestParam("cardNumber") String card) throws IOException {
        if (cardDAO.findCardByNumber(card) != null){
            response.getWriter().write("exist");
        } else {
            response.getWriter().write("not_exist");
        }
    }
}
