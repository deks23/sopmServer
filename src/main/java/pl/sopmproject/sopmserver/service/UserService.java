package pl.sopmproject.sopmserver.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sopmproject.sopmserver.model.entity.User;
import pl.sopmproject.sopmserver.model.response.LoginResponse;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Transactional
    public Response registerUser(String userName, CharSequence password){
        Response response = null;
        if(StringUtils.isBlank(password) || StringUtils.isBlank(userName) || userRepository.existsUserByUsername(userName)){
            return new Response(false);
        }
        User user = createUser(userName, securityService.getHashedPassword(password));
        userRepository.save(user);
        response = new Response(true);
        return response;
    }

    public Response loginUser(String userName, CharSequence password){
        if(StringUtils.isBlank(password) || StringUtils.isBlank(userName)){
            return new Response(false);
        }
        Optional<User> userOptional = userRepository.findUserByUsername(userName);
        if( !userOptional.isPresent() || !securityService.validatePassword(userOptional.get(), password)){
            return new Response(false);
        }
        String jwt = securityService.createJWT(userOptional.get());
        LoginResponse loginResponse = new LoginResponse(true);
        loginResponse.setJwt(jwt);
        loginResponse.setUserName(userName);
        return loginResponse;
    }

    private User createUser(String userName, CharSequence password) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password.toString());
        user.setLoggedIn(false);
        return user;
    }


}
