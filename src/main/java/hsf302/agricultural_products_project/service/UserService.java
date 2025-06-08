package hsf302.agricultural_products_project.service;

import hsf302.agricultural_products_project.dto.UserDTO;
import hsf302.agricultural_products_project.model.User;

public interface UserService {
    User save(UserDTO user);
    User findByUserName(String userName);
}
