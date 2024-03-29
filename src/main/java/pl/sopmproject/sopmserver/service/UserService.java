package pl.sopmproject.sopmserver.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sopmproject.sopmserver.exception.JwtParseException;
import pl.sopmproject.sopmserver.model.entity.User;
import pl.sopmproject.sopmserver.model.response.LoginResponse;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.model.util.Constants;
import pl.sopmproject.sopmserver.repository.UserRepository;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserDataService userDataService;

    public Response registerUser(String username, CharSequence password) {
        Response response = null;
        if (isBlank(password) || isBlank(username) || userRepository.existsUserByUsername(username)) {
            logger.warn(Constants.INVALID_REGISTER_CREDENTIALS);
            return Response.builder().status(false).build();
        }
        User user = createUser(username, securityService.getHashedPassword(password));
        userRepository.save(user);
        response = Response.builder().status(true).build();
        return response;
    }

    public Response loginUser(String username, CharSequence password) {
        if (isBlank(password) || isBlank(username)) {
            logger.warn(Constants.BLANK_LOGIN_CREDENTIALS);
            return Response.builder().status(false).build();
        }
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (!userOptional.isPresent() || !securityService.validatePassword(userOptional.get(), password)) {
            logger.warn(Constants.INVALID_LOGIN_CREDENTIALS);
            return Response.builder().status(false).build();
        }
        boolean needData = userDataService.checkIfAdditionalDataNotPresent(userOptional.get());
        String jwt = securityService.createJWT(userOptional.get());
        return LoginResponse.loginBuilder()
                .status(true)
                .needData(needData)
                .jwt(jwt)
                .username(username)
                .build();
    }

    public User getUser(String jwt) throws JwtParseException {
        Long userId = securityService.getUserIdFromJWT(jwt);
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            logger.warn(Constants.USER_NOT_FOUND + userId);
            throw new JwtParseException();
        } else {
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
