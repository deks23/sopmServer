package pl.sopmproject.sopmserver.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sopmproject.sopmserver.model.response.Response;
import pl.sopmproject.sopmserver.repository.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Mock
    SecurityService securityService;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void testUserRegistration(){
        Mockito.when(securityService.getHashedPassword(Mockito.anyString())).thenReturn("hashedPassword");
        Mockito.when(userRepository.existsUserByUsername(Mockito.anyString())).thenReturn(false);

        Response response = userService.registerUser("username", "password");
        Assert.assertTrue(response.isStatus());
    }

    @Test
    public void testUserRegistrationUserExists(){
        Mockito.when(securityService.getHashedPassword(Mockito.anyString())).thenReturn("hashedPassword");
        Mockito.when(userRepository.existsUserByUsername(Mockito.anyString())).thenReturn(true);

        Response response = userService.registerUser("username", "password");
        Assert.assertFalse(response.isStatus());
    }

    /*User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    Mockito.when(userRepository.findUserByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
    Mockito.when(userRepository.save(Mockito.any())).then;*/
}
