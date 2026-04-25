package JobPortal.SpringJobPortal.Security.CurrentUserAuth;

import JobPortal.SpringJobPortal.Entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service

public class CurrentUserService {
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("No authorized user found");

        }

        return (User) authentication.getPrincipal();
    }
}
