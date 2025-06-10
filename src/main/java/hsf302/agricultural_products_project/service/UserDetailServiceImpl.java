package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
     @Autowired
    private UserRepository userRepository;

     @Autowired
     private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUserName(username);
    if (user == null){
        throw new UsernameNotFoundException("User not found");

    }
        return new CustomUserDetails(user);

    }
}
