package pl.sopmproject.sopmserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.sopmproject.sopmserver.exception.JwtParseException;
import pl.sopmproject.sopmserver.model.entity.User;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.repository.UserRepository;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class UserDataService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public boolean checkIfAdditionalDataNotPresent(User user) {
        return (isBlank(user.getGender()) || user.getAge() == 0);
    }

    public Response updateUserData(String jwt, String gender, int age) {
        User user = null;
        try {
            user = userService.getUser(jwt);
        } catch (JwtParseException e) {
            return Response.builder().status(false).httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        if (age > 0) {
            user.setAge(age);
        }
        if (isNotBlank(gender)) {
            user.setGender(gender);
        }
        userRepository.save(user);
        return Response.builder().status(true).httpStatus(HttpStatus.OK).build();
    }
}
