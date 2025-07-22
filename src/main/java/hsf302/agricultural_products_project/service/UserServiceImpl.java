package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.UserDTO;
import hsf302.agricultural_products_project.dto.UserProfileDTO;
import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.repository.UserRepository;
import hsf302.agricultural_products_project.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;



    @Override
    public User save(UserDTO userDTO) {
        User newUser = new User();

        newUser.setUserName(userDTO.getUserName());
        newUser.setUserFullName(userDTO.getUserFullName());
        newUser.setPassword(PasswordUtils.hashPassword(userDTO.getPassword()));
        newUser.setRole(Role.ROLE_MEMBER);
        newUser.setStatus(true);
        newUser.setAddress(userDTO.getAddress());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        return userRepository.save(newUser);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateStatus(Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.setStatus(false);
        userRepository.save(user);
    }

    @Override
    public void updateProfile(UserProfileDTO user) {
        User existingUser = userRepository.findById(user.getUserId()).orElse(null);
        if(existingUser != null) {
            existingUser.setUserName(user.getUserName());
            existingUser.setUserFullName(user.getUserFullName());
            existingUser.setPassword(user.getPassword());
            existingUser.setAddress(user.getAddress());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            userRepository.save(existingUser);
        }
    }
}
