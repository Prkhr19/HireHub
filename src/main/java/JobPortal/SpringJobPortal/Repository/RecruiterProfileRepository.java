package JobPortal.SpringJobPortal.Repository;

import JobPortal.SpringJobPortal.Entity.RecruiterProfile;
import JobPortal.SpringJobPortal.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long> {
    Optional<RecruiterProfile> findByUser(User user);
}