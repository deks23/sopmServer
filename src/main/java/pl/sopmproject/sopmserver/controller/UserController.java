package pl.sopmproject.sopmserver.controller;


import com.microsoft.applicationinsights.TelemetryClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        logger.info("Login request: ");
        return "LOGIN TEST";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(){
        logger.info("Register request: ");
        return "register";
    }
}
