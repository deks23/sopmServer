package pl.sopmproject.sopmserver.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sopmproject.sopmserver.exception.JwtParseException;
import pl.sopmproject.sopmserver.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityServiceTest {
    @Autowired
    SecurityService securityService;

    @Test
    public void createJWTTest(){
        User user = new User();
        user.setId(12L);
        user.setUsername("User");
        String token = securityService.createJWT(user);
        Assert.assertNotNull(token);
    }

    @Test
    public void getUserIdFromJwtTest(){
        User user = new User();
        user.setId(12L);
        user.setUsername("User");
        String token = securityService.createJWT(user);
        Long id = null;
        try {
            id = securityService.getUserIdFromJWT(token);
        } catch (JwtParseException e) {
            Assert.assertTrue(false);
        }
        Assert.assertEquals(user.getId(), id);
    }

}
