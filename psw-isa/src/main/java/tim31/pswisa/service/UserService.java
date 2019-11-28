package tim31.pswisa.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import tim31.pswisa.repository.UserRepository;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.model.User;
 
@Service
public class UserService {
 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TokenUtils tokenUtils;
    
    public User findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
   
    public List<User> findAllByActivated(Boolean b){
        return userRepository.findAllByActivated(b);
    }
    
       
    public User save(User user) {
        return userRepository.save(user);
    }
}