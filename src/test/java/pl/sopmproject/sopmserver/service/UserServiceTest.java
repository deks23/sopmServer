package pl.sopmproject.sopmserver.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sopmproject.sopmserver.model.User;
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

        boolean userRegistered = userService.registerUser("username", "password");
        Assert.assertTrue(userRegistered);
    }

    @Test
    public void testUserRegistrationUserExists(){
        Mockito.when(securityService.getHashedPassword(Mockito.anyString())).thenReturn("hashedPassword");
        Mockito.when(userRepository.existsUserByUsername(Mockito.anyString())).thenReturn(true);

        boolean userRegistered = userService.registerUser("username", "password");
        Assert.assertFalse(userRegistered);
    }

    /*User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    Mockito.when(userRepository.findUserByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
    Mockito.when(userRepository.save(Mockito.any())).then;*/
}
