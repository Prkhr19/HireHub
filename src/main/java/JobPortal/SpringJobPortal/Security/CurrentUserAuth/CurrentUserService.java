package JobPortal.SpringJobPortal.Security.CurrentUserAuth;

import JobPortal.SpringJobPortal.Entity.User;
import JobPortal.SpringJobPortal.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("No authorized user found");

        }
        Object principal =
                authentication.getPrincipal();

        if (principal instanceof User user) {
            return user;
        }

        if (principal instanceof String email) {
            return userRepository
                    .findByEmail(email)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(
                                    "User not found"));
        }
        throw new IllegalArgumentException(
                "Unsupported principal type");

    }

}
