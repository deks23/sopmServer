package pl.sopmproject.sopmserver.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sopmproject.sopmserver.exception.LoginException;
import pl.sopmproject.sopmserver.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, params = {"userName","password" })
    public ResponseEntity login(@RequestParam String userName, @RequestParam CharSequence password){
        logger.info("Login request: " + userName);
        Map<String, Object> responseMap = null;
        try {
            responseMap = userService.loginUser(userName, password);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestParam String userName, @RequestParam CharSequence password){
        logger.info("Register request: " + userName);
        if (userService.registerUser(userName, password)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
