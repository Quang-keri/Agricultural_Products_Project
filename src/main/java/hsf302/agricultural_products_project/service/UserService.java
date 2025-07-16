package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.UserDTO;
import hsf302.agricultural_products_project.model.User;

import java.util.List;

public interface UserService {
    User save(UserDTO user);
    User findByUserName(String userName);
    List<User> findAll();
    User findById(Long id);
    User saveUser(User user);
    void updateStatus(Long id);
    void deleteById(Long id);
    void updateUser(User user);


}
