package pl.sopmproject.sopmserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/login")
    public String login(){
        return "LOGIN TEST";
    }
}
