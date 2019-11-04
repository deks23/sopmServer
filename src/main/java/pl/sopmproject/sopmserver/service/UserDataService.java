package pl.sopmproject.sopmserver.service;

import org.springframework.stereotype.Service;
import pl.sopmproject.sopmserver.model.entity.User;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class UserDataService {
    public boolean checkIfAdditionalDataPresent(User user){
        if(isBlank(user.getGender()) || user.getAge() == 0){
            return false;
        }else{
            return true;
        }
    }
}
