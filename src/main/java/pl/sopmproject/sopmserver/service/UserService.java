package pl.sopmproject.sopmserver.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sopmproject.sopmserver.exception.JwtParseException;
import pl.sopmproject.sopmserver.exception.UserNotFoundException;
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


    public Response registerUser(String username, CharSequence password){
        Response response = null;
        if(StringUtils.isBlank(password) || StringUtils.isBlank(username) || userRepository.existsUserByUsername(username)){
            return Response.builder().status(false).build();
        }
        User user = createUser(username, securityService.getHashedPassword(password));
        userRepository.save(user);
        response = Response.builder().status(true).build();
        return response;
    }

    public Response loginUser(String username, CharSequence password){
        if(StringUtils.isBlank(password) || StringUtils.isBlank(username)){
            return Response.builder().status(false).build();
        }
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if( !userOptional.isPresent() || !securityService.validatePassword(userOptional.get(), password)){
            return Response.builder().status(false).build();
        }
        String jwt = securityService.createJWT(userOptional.get());
        LoginResponse loginResponse = LoginResponse.loginBuilder().status(true).build();
        loginResponse.setJwt(jwt);
        loginResponse.setUsername(username);
        return loginResponse;
    }

    public User getUser(String jwt) throws JwtParseException {
        Long userId = securityService.getUserIdFromJWT(jwt);
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new JwtParseException();
        }else{
            return user.get();
        }
    }

    private User createUser(String username, CharSequence password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password.toString());
        user.setLoggedIn(false);
        return user;
    }


}
