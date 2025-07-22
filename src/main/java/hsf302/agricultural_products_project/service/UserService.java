package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.UserDTO;
import hsf302.agricultural_products_project.dto.UserProfileDTO;
import hsf302.agricultural_products_project.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User save(UserDTO user);
    User findByUserName(String userName);
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void updateStatus(Long id);
    void updateProfile(UserProfileDTO user);

}
