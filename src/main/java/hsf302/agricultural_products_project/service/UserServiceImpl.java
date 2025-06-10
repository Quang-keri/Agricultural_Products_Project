package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.UserDTO;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User save(UserDTO userDTO) {
        User newUser = new User();
        newUser.setUserName(userDTO.getUserName());
        newUser.setUserFullName(userDTO.getUserFullName());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRole("ROLE_MEMBER");
        newUser.setStatus("active");
        newUser.setAddress(userDTO.getAddress());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        return userRepository.save(newUser);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


}
