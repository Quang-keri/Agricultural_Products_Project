package hsf302.agricultural_products_project.config;

import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CustomUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static CustomUserDetails getSessionUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return (CustomUserDetails) principal;
            }
        }
        return null;
    }

    //cái này ae lấy khi cần username của user
    public static String getSessionUsername() {
        CustomUserDetails userDetails = getSessionUserDetails();
        return userDetails != null ? userDetails.getUsername() : null;
    }

    // cái này ae lấy khi cần full thông tin user thường lấy cái này
    public static User getUserWithoutPassword() {
        CustomUserDetails userDetails = getSessionUserDetails();
        if (userDetails != null) {
            User user = userDetails.getUser();
            user.setPassword(null); // set password đã mã hóa
            return user;
        }
        return null;
    }

    public static Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUser().getUserId(); // tuỳ thuộc cấu trúc project
    }
}

