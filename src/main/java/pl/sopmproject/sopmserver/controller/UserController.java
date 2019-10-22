package pl.sopmproject.sopmserver.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sopmproject.sopmserver.model.request.UserValidationRequest;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.service.UserService;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login")
    @PostMapping
    public ResponseEntity login(@ModelAttribute UserValidationRequest loginRequest){
        logger.info("Login request: " + loginRequest.getUserName());

        Response response = userService.loginUser(loginRequest.getUserName(), loginRequest.getPassword());
        if(response.isStatus()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }


    @RequestMapping(value = "/register")
    @PostMapping
    public ResponseEntity register(@ModelAttribute UserValidationRequest registerRequest){
        logger.info("Register request: " + registerRequest.getUserName());

        Response response = userService.registerUser(registerRequest.getUserName(), registerRequest.getPassword());
        if (response.isStatus()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}