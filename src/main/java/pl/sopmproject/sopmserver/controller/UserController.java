package pl.sopmproject.sopmserver.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sopmproject.sopmserver.model.request.UpdateDataRequest;
import pl.sopmproject.sopmserver.model.request.UserValidationRequest;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.model.util.Constants;
import pl.sopmproject.sopmserver.service.SecurityService;
import pl.sopmproject.sopmserver.service.UserDataService;
import pl.sopmproject.sopmserver.service.UserService;
import pl.sopmproject.sopmserver.util.DateConverter;

import java.util.Map;


@RestController
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    private static final String LOGIN = "/user/login";
    private static final String REGISTER = "/user/register";
    private static final String UPDATE_DATA = "/user/updateData";


    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserDataService userDataService;

    @RequestMapping(value = LOGIN)
    @PostMapping
    public ResponseEntity login(@RequestBody UserValidationRequest loginRequest){
        logger.info("Login request: " + loginRequest.getUsername());

        Response response = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if(response.isStatus()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    @RequestMapping(value = REGISTER)
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

    @RequestMapping(value = UPDATE_DATA)
    @PostMapping
    public ResponseEntity updateUserData(@RequestHeader Map<String, String> headers, @RequestBody UpdateDataRequest updateDataRequest){
        Response response = null;
        if (!headers.containsKey(Constants.JWT)) {
            response = Response.builder().status(false).responseMessage(Constants.TOKEN_NOT_PRESENT).build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        String jwt = headers.get(Constants.JWT);
        response = userDataService.updateUserData(jwt, updateDataRequest.getGender(), DateConverter.jodaToJavaTime(updateDataRequest.getBirthday()));
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}