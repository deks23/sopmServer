package pl.sopmproject.sopmserver.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sopmproject.sopmserver.model.request.UserValidationRequest;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.service.SecurityService;
import pl.sopmproject.sopmserver.service.UserService;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/login")
    @PostMapping
    public ResponseEntity login(@RequestBody UserValidationRequest loginRequest){
        logger.info("Login request: " + loginRequest.getUsername());

        Response response = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if(response.isStatus()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }


    @RequestMapping(value = "/register")
    @PostMapping
    public ResponseEntity register(@RequestBody UserValidationRequest registerRequest){
        logger.info("Register request: " + registerRequest.getUsername());

        Response response = userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
        if (response.isStatus()) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}