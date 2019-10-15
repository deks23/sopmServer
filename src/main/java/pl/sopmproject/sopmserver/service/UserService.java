package pl.sopmproject.sopmserver.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sopmproject.sopmserver.exception.LoginException;
import pl.sopmproject.sopmserver.model.User;
import pl.sopmproject.sopmserver.repository.UserRepository;
import pl.sopmproject.sopmserver.utils.ResponseKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Transactional
    public boolean registerUser(String userName, CharSequence password){
        if(StringUtils.isBlank(password) || StringUtils.isBlank(userName) || userRepository.existsUserByUsername(userName)){
            return false;
        }

        User user = createUser(userName, securityService.getHashedPassword(password));
        userRepository.save(user);
        return true;
    }

    public Map<String, Object> loginUser(String userName, CharSequence password) throws LoginException {
        if(StringUtils.isBlank(password) || StringUtils.isBlank(userName)){
            throw new LoginException();
        }
        Optional<User> userOptional = userRepository.findUserByUsername(userName);
        if( !userOptional.isPresent() || !securityService.validatePassword(userOptional.get(), password) || !securityService.validatePassword(userOptional.get(), password)){
            throw new LoginException();
        }
        String jwt = securityService.createJWT(userOptional.get());
        Map<String, Object> response = new HashMap<>();
        response.put(ResponseKeys.USER_NAME, userOptional.get().getUsername());
        response.put(ResponseKeys.JWT, jwt);
        return response;
    }

    private User createUser(String userName, CharSequence password) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password.toString());
        user.setLoggedIn(false);
        return user;
    }


}
