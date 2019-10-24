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
    public Response registerUser(String username, CharSequence password){
        Response response = null;
        if(StringUtils.isBlank(password) || StringUtils.isBlank(username) || userRepository.existsUserByUsername(username)){
            return new Response(false);
        }
        User user = createUser(username, securityService.getHashedPassword(password));
        userRepository.save(user);
        response = new Response(true);
        return response;
    }

    public Response loginUser(String username, CharSequence password){
        if(StringUtils.isBlank(password) || StringUtils.isBlank(username)){
            return new Response(false);
        }
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if( !userOptional.isPresent() || !securityService.validatePassword(userOptional.get(), password)){
            return new Response(false);
        }
        String jwt = securityService.createJWT(userOptional.get());
        LoginResponse loginResponse = new LoginResponse(true);
        loginResponse.setJwt(jwt);
        loginResponse.setUsername(username);
        return loginResponse;
    }

    private User createUser(String username, CharSequence password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password.toString());
        user.setLoggedIn(false);
        return user;
    }


}
